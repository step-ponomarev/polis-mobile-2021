package ru.mail.polis.ui.fragments.apartment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount
import ru.mail.polis.viewModels.ApartmentViewModel

class AddApartmentFragment : ApartmentFragment() {

    private lateinit var addApartmentButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_apartment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        metroCircleIv = view.findViewById(R.id.fragment_add_apartment__circle)
        addApartmentButton = view.findViewById(R.id.add_button)
        costEditText = view.findViewById(R.id.fragment_add_apartment__set_cost_et)
        squareEditText = view.findViewById(R.id.fragment_add_apartment__set_squared_metres_et)
        chipGroup = view.findViewById(R.id.component_rooms__chip_group)
        addPhotoImageButton = view.findViewById(R.id.fragment_add_apartment__add_image_button)
        photoLinearLayout = view.findViewById(R.id.fragment_add_apartment__photo_linear_layout)

        apartmentViewModel = ViewModelProvider(this).get(ApartmentViewModel::class.java)

        initSpinner(view)

        addApartmentButton.setOnClickListener(this::onClickAddApartment)
        addPhotoImageButton.setOnClickListener(this::onClickAddPhoto)
    }

    private fun onClickAddApartment(view: View) {

        val selectedChip = chipGroup.findViewById<Chip>(chipGroup.checkedChipId)

        if (selectedChip == null) {
            getToastWithText(getString(R.string.toast_fill_all_information_about_apartment)).show()
            return
        }

        val metro = spinner.selectedItem.toString()
        val rooms = selectedChip.text.toString()
        val cost = costEditText.text.toString()
        val square = squareEditText.text.toString()

        if (metro.isEmpty() || rooms.isBlank() || cost.isBlank() || square.isBlank()) {
            getToastWithText(getString(R.string.toast_fill_all_information_about_apartment)).show()
            return
        }

        val email = getEmail()
        GlobalScope.launch(Dispatchers.Main) {
            val user = apartmentViewModel.fetchUser(email)
                ?: throw java.lang.IllegalStateException("Null user by email: $email")

            val apartmentED = ApartmentED.Builder
                .createBuilder()
                .email(email)
                .ownerName("${user.name} ${user.surname}")
                .ownerAge(user.age!!)
                .metro(Metro.from(metro))
                .roomCount(RoomCount.from(rooms))
                .ownerAvatar(user.photo)
                .apartmentCosts(cost.toLong())
                .apartmentSquare(square.toLong())
                .build()

            apartmentViewModel.addApartment(apartmentED)
        }

        findNavController().navigate(R.id.nav_graph__list_of_people)
    }
}

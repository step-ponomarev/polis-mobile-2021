package ru.mail.polis.ui.fragments.apartment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.metro.Metro
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.room.RoomCount
import ru.mail.polis.utils.StorageUtils

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

        addApartmentButton = view.findViewById(R.id.add_button)
        addApartmentButton.setOnClickListener(this::onClickAddApartment)
    }

    private fun onClickAddApartment(view: View) {
        val selectedChip = chipGroup.findViewById<Chip>(chipGroup.checkedChipId)

        if (selectedChip == null) {
            NotificationCenter.showDefaultToast(requireContext(), getString(R.string.toast_fill_all_information_about_apartment))
            return
        }

        val metro = spinner.selectedItem.toString()
        val rooms = selectedChip.text.toString()
        val cost = costEditText.text.toString()
        val square = squareEditText.text.toString()

        if (metro.isEmpty() || rooms.isBlank() || cost.isBlank() || square.isBlank()) {
            NotificationCenter.showDefaultToast(requireContext(), getString(R.string.toast_fill_all_information_about_apartment))
            return
        }

        val email = StorageUtils.getCurrentUserEmail(requireContext())
        GlobalScope.launch(Dispatchers.Main) {
            try {
                apartmentViewModel.fetchUser(email)
                    ?: throw IllegalStateException("Null user by email: $email")

                val apartmentED = ApartmentED.Builder
                    .createBuilder()
                    .email(email)
                    .metro(Metro.from(metro))
                    .roomCount(RoomCount.from(rooms))
                    .apartmentCosts(cost.toLong())
                    .apartmentSquare(square.toLong())
                    .build()

                apartmentViewModel.addApartment(apartmentED)
                findNavController().navigate(R.id.nav_graph__list_of_people)
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(requireContext(), getString(e.getResourceStringCode()))
            }
        }
    }
}

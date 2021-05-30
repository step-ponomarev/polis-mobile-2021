package ru.mail.polis.ui.fragments.advert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

class AdvertCreationFragment : AdvertFragment() {

    private lateinit var addAdvertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advert_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAdvertButton = view.findViewById(R.id.fragment_advert_creation__continue_btn)

        val email = getEmail()
        GlobalScope.launch(Dispatchers.Main) {
            user = viewModel.fetchUser(email)
                ?: throw IllegalStateException("User not found by email: $email")

            if (user.photo != null) {
                Glide.with(avatarImageView).load(user.photo).into(avatarImageView)
            }

            nameTextView.text = "${user.name} ${user.surname}"
            ageTextView.text = user.age.toString()
        }

        addAdvertButton.setOnClickListener(this::onClickAddAdvert)
    }

    private fun onClickAddAdvert(view: View) {

        val checkedChips = chipGroup.checkedChipIds

        val roomList = mutableListOf<RoomCount>()

        checkedChips.forEach { chipId ->
            val selectedChip = chipGroup.findViewById<Chip>(chipId)
            roomList.add(RoomCount.from(selectedChip.text.toString()))
        }

        val metro = spinner.selectedItem.toString()
        val costFrom = costFromEditText.text.toString()
        val costTo = costToEditText.text.toString()
        val aboutMe = aboutMeEditText.text.toString()

        if (metro.isBlank() ||
            roomList.isEmpty() ||
            costFrom.isBlank() ||
            costTo.isBlank()
        ) {
            getToastWithText(getString(R.string.toast_fill_all_advert_info)).show()
            return
        }

        val email = getEmail()
        GlobalScope.launch(Dispatchers.Main) {

            val user = viewModel.fetchUser(email)
                ?: throw IllegalStateException("Null user by email: $email")

            val person = PersonED.Builder.createBuilder()
                .email(email)
                .metro(Metro.from(metro))
                .description(aboutMe)
                .money(costFrom.toLong(), costTo.toLong())
                .rooms(roomList)
                .tags(emptyList())
                .build()

            viewModel.addPerson(person)
            findNavController().navigate(R.id.nav_graph__list_of_people)
        }
    }
}

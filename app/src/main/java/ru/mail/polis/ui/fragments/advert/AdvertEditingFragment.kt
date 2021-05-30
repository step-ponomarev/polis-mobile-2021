package ru.mail.polis.ui.fragments.advert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.forEach
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount
import java.util.Collections

class AdvertEditingFragment : AdvertFragment() {

    private lateinit var editAdvertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advert_editing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editAdvertButton = view.findViewById(R.id.fragment_advert_editing__continue_btn)

        editAdvertButton.setOnClickListener(this::onClickEditButton)

        val email = getEmail()

        GlobalScope.launch(Dispatchers.Main) {
            val user = viewModel.fetchUser(email)
                ?: throw IllegalStateException("Null user by email: $email")

            val personED: PersonED? = viewModel.getPersonByEmail(email)

            if (personED != null) {
                fillFields(personED, user)
            } else {
                getToastWithText(getString(R.string.toast_there_are_no_advert_to_edit))
            }
        }
    }

    private fun onClickEditButton(view: View) {

        val selectedChip = chipGroup.findViewById<Chip>(chipGroup.checkedChipId)
        if (selectedChip == null) {
            getToastAboutFillAllFields().show()
            return
        }

        val metro = spinner.selectedItem.toString()
        val roomCount = selectedChip.text.toString()
        val costFrom = costFromEditText.text.toString()
        val costTo = costToEditText.text.toString()
        val aboutMe = aboutMeEditText.text.toString()

        if (metro.isBlank() ||
            roomCount.isBlank() ||
            costFrom.isBlank() ||
            costTo.isBlank()
        ) {
            getToastAboutFillAllFields().show()
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
                .rooms(Collections.singletonList(RoomCount.from(roomCount)))
                .tags(emptyList())
                .build()

            viewModel.updatePerson(person)
        }
    }

    private fun fillFields(personED: PersonED, user: UserED) {

        Glide.with(avatarImageView).load(user.photo).into(avatarImageView)

        spinner.setSelection(Metro.values().indexOf(personED.metro))

        val rooms = personED.rooms

        rooms.forEach { room ->
            chipGroup.forEach { view ->
                if (view is Chip) {
                    if (view.text == room.label) {
                        view.isChecked = true
                    }
                }
            }
        }

        costFromEditText.setText(personED.moneyFrom.toString())
        costToEditText.setText(personED.moneyTo.toString())
        aboutMeEditText.setText(personED.description)
    }
}

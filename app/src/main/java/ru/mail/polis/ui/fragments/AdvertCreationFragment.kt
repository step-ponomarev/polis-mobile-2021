package ru.mail.polis.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount
import ru.mail.polis.viewModels.AdvertCreationViewModel
import java.util.Collections

class AdvertCreationFragment : Fragment() {
    private val viewModel = AdvertCreationViewModel()

    private lateinit var email: String
    private lateinit var user: UserED

    private lateinit var spinner: Spinner
    private lateinit var chipGroup: ChipGroup
    private lateinit var avatarImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var costFromEditText: EditText
    private lateinit var costToEditText: EditText
    private lateinit var aboutMeEditText: EditText
    private lateinit var createAdvertFragment: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advert_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.fragment_advert_creation__spinner)
        val metroNamesList = Metro.values().map { it.stationName }
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, metroNamesList)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = it
            }

        chipGroup = view.findViewById(R.id.component_rooms__chip_group)
        costFromEditText = view.findViewById(R.id.fragment_advert_creation__from_price_et)
        costToEditText = view.findViewById(R.id.fragment_advert_creation__to_price_et)
        aboutMeEditText = view.findViewById(R.id.fragment_advert_creation__about_me_et)
        createAdvertFragment = view.findViewById(R.id.fragment_advert_creation__continue_btn)
        avatarImageView = view.findViewById(R.id.component_person_header__avatar)
        nameTextView = view.findViewById(R.id.component_person_header__name)
        ageTextView = view.findViewById(R.id.component_person_header__age)

        email = getEmail()
        GlobalScope.launch(Dispatchers.Main) {
            user = viewModel.fetchUser(email)
                ?: throw IllegalStateException("User not found by email: $email")

            if (user.photo != null) {
                Glide.with(avatarImageView).load(user.photo).into(avatarImageView)
            }

            nameTextView.text = "${user.name} ${user.surname}"
            ageTextView.text = user.age.toString()
        }

        createAdvertFragment.setOnClickListener(this::createAdvert)
    }

    private fun createAdvert(view: View) {
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

        GlobalScope.launch(Dispatchers.Main) {
            val person = PersonED.Builder.createBuilder()
                .email(email)
                .age(user.age!!)
                .name("${user.name} ${user.surname}")
                .metro(Metro.from(metro))
                .description(aboutMe)
                .money(costFrom.toLong(), costTo.toLong())
                .rooms(Collections.singletonList(RoomCount.from(roomCount)))
                .tags(emptyList())
                .build()

            if (person.photo != null) {
                person.photo = person.photo
            }

            viewModel.addPerson(person)
            findNavController().navigate(R.id.nav_graph__list_of_people)
        }
    }

    private fun getToastAboutFillAllFields(): Toast {
        return Toast.makeText(
            requireContext(),
            getString(R.string.toast_fill_all_advert_info),
            Toast.LENGTH_SHORT
        )
    }

    private fun getEmail(): String {
        return activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )?.getString(getString(R.string.preference_email_key), null)
            ?: throw IllegalStateException("Email not found")
    }
}

package ru.mail.polis.ui.fragments.advert

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import ru.mail.polis.R
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.metro.Metro
import ru.mail.polis.viewModels.AdvertViewModel

abstract class AdvertFragment : Fragment() {
    protected val viewModel = AdvertViewModel()

    protected lateinit var user: UserED

    protected lateinit var spinner: Spinner
    protected lateinit var chipGroup: ChipGroup
    protected lateinit var avatarImageView: ImageView
    protected lateinit var nameTextView: TextView
    protected lateinit var ageTextView: TextView
    protected lateinit var costFromEditText: EditText
    protected lateinit var costToEditText: EditText
    protected lateinit var aboutMeEditText: EditText

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
        avatarImageView = view.findViewById(R.id.component_person_header__avatar)
        nameTextView = view.findViewById(R.id.component_person_header__name)
        ageTextView = view.findViewById(R.id.component_person_header__age)
    }

    protected fun getToastAboutFillAllFields(): Toast {
        return Toast.makeText(
            requireContext(),
            getString(R.string.toast_fill_all_advert_info),
            Toast.LENGTH_SHORT
        )
    }

    protected fun getEmail(): String {
        return activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )?.getString(getString(R.string.preference_email_key), null)
            ?: throw IllegalStateException("Email not found")
    }
}

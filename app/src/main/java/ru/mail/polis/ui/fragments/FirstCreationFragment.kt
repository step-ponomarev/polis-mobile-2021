package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import ru.mail.polis.R
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.viewModels.FirstCreationViewModel

class FirstCreationFragment : Fragment() {

    private lateinit var changePhotoButton: Button
    private lateinit var continueButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var spinner: Spinner
    private lateinit var firstCreationViewModel: FirstCreationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filling_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changePhotoButton = view.findViewById(R.id.fragment_filling_profile_info_edit_btn)
        continueButton = view.findViewById(R.id.fragment_filling_profile_info_continue_btn)
        nameEditText = view.findViewById(R.id.fragment_filling_profile_info_name_et)
        surnameEditText = view.findViewById(R.id.fragment_filling_profile_info_surname_et)
        phoneEditText = view.findViewById(R.id.fragment_filling_profile_info_phone_et)
        ageEditText = view.findViewById(R.id.fragment_filling_profile_info_age_et)
        spinner = view.findViewById(R.id.fragment_filling_profile_info_gender_sp)
        firstCreationViewModel = ViewModelProvider(this).get(FirstCreationViewModel::class.java)

        changePhotoButton.setOnClickListener(this::onChangePhotoButton)
        continueButton.setOnClickListener(this::onContinueButton)
    }

    private fun onChangePhotoButton(view: View) {

    }

    private fun onContinueButton(view: View) {

        if (!checkField()) {
            getToastAboutFillAllFields()
        }

        firstCreationViewModel.addUser(
            UserED(
                email = FirebaseAuth.getInstance().currentUser.email!!,
                name = nameEditText.text.toString(),
                surname = surnameEditText.text.toString(),
                age = Integer.parseInt(ageEditText.text.toString()).toLong(),
                phone = phoneEditText.text.toString(),
                photo = "",
                externalAccounts = emptyList()
            )
        )
    }

    private fun checkField(): Boolean {
        if (nameEditText.text.toString().isEmpty() || surnameEditText.text.toString().isEmpty()
            || ageEditText.text.toString().isEmpty() || phoneEditText.text.toString().isEmpty()
        ) {
            return false
        }

        return true
    }

    private fun getToastAboutFillAllFields(): Toast {
        return Toast.makeText(
            requireContext(),
            getString(R.string.fill_all_information_about_user),
            Toast.LENGTH_SHORT
        )
    }

}

package ru.mail.polis.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import ru.mail.polis.R
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.decoder.DecoderFactory
import ru.mail.polis.viewModels.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var editButton: Button
    private lateinit var changePhotoButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var addExternalAccountImageButton: AppCompatButton
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var avatar: ShapeableImageView

    private var currentPhotoUrl: String? = null

    private val takePhotoFromGallery =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResult
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editButton = view.findViewById(R.id.fragment_settings__button_edit)
        changePhotoButton = view.findViewById(R.id.change_avatar_component__edit_button)
        nameEditText = view.findViewById(R.id.fragment_settings__et_name)
        surnameEditText = view.findViewById(R.id.fragment_settings__et_surname)
        phoneEditText = view.findViewById(R.id.fragment_settings__et_phone)
        ageEditText = view.findViewById(R.id.fragment_settings__et_age)
        addExternalAccountImageButton =
            view.findViewById(R.id.fragment_settings__add_external_account_button)
        avatar = view.findViewById(R.id.change_avatar_component__people_item_iv_photo)

        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        settingsViewModel.getUserInfo(getEmail())

        settingsViewModel.getUser().observe(
            viewLifecycleOwner,
            { userED ->
                nameEditText.setText(userED.name)
                surnameEditText.setText(userED.surname)
                phoneEditText.setText(userED.phone)
                ageEditText.setText(userED.age.toString())
                Glide.with(avatar).load(userED.photo).into(avatar)
                currentPhotoUrl = userED.photo
            }
        )

        editButton.setOnClickListener(this::onClickEditUser)
        changePhotoButton.setOnClickListener(this::onClickChangePhoto)
        addExternalAccountImageButton.setOnClickListener(this::onClickAddExternalAccount)
    }

    private fun onClickChangePhoto(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        takePhotoFromGallery.launch(intent)
    }

    private fun onClickEditUser(view: View) {

        Log.d("CHECKBIT", currentPhotoUrl ?: "null")

        val photo: String? = if (currentPhotoUrl == null) {
            null
        } else {
            currentPhotoUrl
        }

        val bitmap: Bitmap? = if (currentPhotoUrl == null) {
            avatar.drawable.toBitmap()
        } else {
            null
        }

        settingsViewModel.updateUser(
            UserED(
                email = getEmail(),
                name = nameEditText.text.toString(),
                surname = surnameEditText.text.toString(),
                phone = phoneEditText.text.toString(),
                age = Integer.parseInt(ageEditText.text.toString()).toLong(),
                externalAccounts = emptyList(),
                photo = photo
            ),
            bitmap
        )

        getToastThatUserChangedInformation().show()
    }

    private fun onClickAddExternalAccount(view: View) {
    }

    private fun getEmail(): String {
        return activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )?.getString(getString(R.string.preference_email_key), null)
            ?: throw IllegalStateException("Email not found")
    }

    private fun handleResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {

            val selectedImage: Uri? = result.data?.data

            val bitmap = DecoderFactory.getImageDecoder(requireContext().contentResolver)
                .decode(selectedImage!!)

            avatar.setImageBitmap(bitmap)
            currentPhotoUrl = null
        }
    }

    private fun getToastThatUserChangedInformation(): Toast {
        return Toast.makeText(
            requireContext(),
            getString(R.string.toast_chages_are_saved),
            Toast.LENGTH_SHORT
        )
    }
}

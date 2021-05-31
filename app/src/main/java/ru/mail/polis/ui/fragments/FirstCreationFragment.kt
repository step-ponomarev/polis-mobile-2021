package ru.mail.polis.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.decoder.DecoderFactory
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.FirstCreationViewModel

class FirstCreationFragment : Fragment() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var changePhotoButton: Button
    private lateinit var continueButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var spinner: Spinner
    private lateinit var firstCreationViewModel: FirstCreationViewModel
    private lateinit var avatar: ShapeableImageView

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
        return inflater.inflate(R.layout.fragment_filling_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changePhotoButton = view.findViewById(R.id.fragment_filling_profile_info__edit_btn)
        continueButton = view.findViewById(R.id.fragment_filling_profile_info_continue__btn)
        nameEditText = view.findViewById(R.id.fragment_filling_profile_info__name_et)
        surnameEditText = view.findViewById(R.id.fragment_filling_profile_info__surname_et)
        phoneEditText = view.findViewById(R.id.fragment_filling_profile_info__phone_et)
        ageEditText = view.findViewById(R.id.fragment_filling_profile_info__age_et)
        spinner = view.findViewById(R.id.fragment_filling_profile_info__gender_sp)
        avatar = view.findViewById(R.id.component_person_header__avatar)
        firstCreationViewModel = ViewModelProvider(this).get(FirstCreationViewModel::class.java)

        changePhotoButton.setOnClickListener(this::onChangePhotoButton)
        continueButton.setOnClickListener(this::onContinueButton)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.coroutineContext.cancelChildren()
    }

    private fun onChangePhotoButton(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        takePhotoFromGallery.launch(intent)
    }

    private fun onContinueButton(view: View) {

        if (!checkField()) {
            NotificationCenter.showDefaultToast(
                requireContext(),
                getString(R.string.toast_fill_all_information_about_user)
            )
            return
        }

        val email = StorageUtils.getCurrentUserEmail(requireContext())
        scope.launch(Dispatchers.IO) {
            val photoSrc = "${Collections.USER.collectionName}Photos/$email-photo.jpg"
            val photo = firstCreationViewModel.uploadPhoto(
                photoSrc,
                avatar.drawable.toBitmap()
            )

            try {
                val user = UserED(
                    email = email,
                    name = nameEditText.text.toString(),
                    surname = surnameEditText.text.toString(),
                    age = Integer.parseInt(ageEditText.text.toString()).toLong(),
                    phone = phoneEditText.text.toString(),
                    photo = photo,
                    externalAccounts = emptyList()
                )

                firstCreationViewModel.addUser(user)
                findNavController().navigate(R.id.nav_graph__self_definition_fragment)
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(R.string.error_dao)
                )
            }
        }
    }

    private fun handleResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {

            val selectedImage: Uri? = result.data?.data

            val bitmap = DecoderFactory.getImageDecoder(requireContext().contentResolver)
                .decode(selectedImage!!)

            avatar.setImageBitmap(bitmap)
        }
    }

    private fun checkField(): Boolean {
        if (nameEditText.text.toString().isEmpty() || surnameEditText.text.toString().isEmpty() ||
            ageEditText.text.toString().isEmpty() || phoneEditText.text.toString().isEmpty()
        ) {
            return false
        }

        return true
    }
}

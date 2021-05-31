package ru.mail.polis.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.R
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.decoder.DecoderFactory
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.SettingsViewModel

class SettingsFragment : Fragment() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var editButton: Button
    private lateinit var changePhotoButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var avatar: ImageView
    private lateinit var apartmentButton: Button
    private lateinit var personButton: Button
    private lateinit var settingsViewModel: SettingsViewModel

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
        avatar = view.findViewById(R.id.change_avatar_component__people_item_iv_photo)
        apartmentButton = view.findViewById(R.id.fragment_settings__edit_apartment)
        personButton = view.findViewById(R.id.fragment_settings__edit_person)

        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        settingsViewModel.fetchUser(StorageUtils.getCurrentUserEmail(requireContext()))
        settingsViewModel.user
            .onEach { userED ->
                nameEditText.setText(userED.name)
                surnameEditText.setText(userED.surname)
                phoneEditText.setText(userED.phone)
                ageEditText.setText(userED.age.toString())
                Glide.with(avatar).load(userED.photo).into(avatar)
                currentPhotoUrl = userED.photo
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        apartmentButton.setOnClickListener(this::onClickApartmentButton)
        editButton.setOnClickListener(this::onClickEditUser)
        changePhotoButton.setOnClickListener(this::onClickChangePhoto)
    }

    private fun onClickChangePhoto(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        takePhotoFromGallery.launch(intent)
    }

    private fun onClickEditUser(view: View) {
        val bitmap: Bitmap? = getBitmap()

        val email = StorageUtils.getCurrentUserEmail(requireContext())
        scope.launch(Dispatchers.Main) {
            val photoSrc = "${Collections.USER.collectionName}Photos/$email-photo.jpg"

            try {
                val photo = uploadPhoto(photoSrc, bitmap)
                val user = UserED(
                    email = StorageUtils.getCurrentUserEmail(requireContext()),
                    name = nameEditText.text.toString(),
                    surname = surnameEditText.text.toString(),
                    phone = phoneEditText.text.toString(),
                    age = Integer.parseInt(ageEditText.text.toString()).toLong(),
                    externalAccounts = emptyList(),
                    photo = photo
                )

                settingsViewModel.updateUser(user)
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(R.string.toast_changes_are_saved)
                )
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    private suspend fun uploadPhoto(path: String, bitmap: Bitmap?): String? {
        return if (currentPhotoUrl == null && bitmap != null) {
            withContext(Dispatchers.IO) {
                settingsViewModel.uploadPhoto(path, bitmap)
            }
        } else currentPhotoUrl
    }

    private fun getBitmap(): Bitmap? {
        if (currentPhotoUrl != null) {
            return null
        }

        return avatar.drawable.toBitmap()
    }

    private fun onClickApartmentButton(view: View) {
        scope.launch(Dispatchers.Main) {
            try {
                val exist = withContext(Dispatchers.IO) {
                    settingsViewModel.checkApartmentExist(StorageUtils.getCurrentUserEmail(requireContext()))
                }

                if (exist) {
                    findNavController().navigate(R.id.nav_graph__edit_apartment_fragment)
                    return@launch
                }

                val dialogFragment =
                    CustomDialogFragment(
                        getString(R.string.dialog_fragment_title_add_apartment),
                        getString(R.string.dialog_fragment_message_add_apartment),
                        { dialog, _ ->
                            dialog.cancel()
                            findNavController().navigate(R.id.nav_graph__add_apartment_fragment)
                        },
                        { dialog, _ ->
                            dialog.cancel()
                        }
                    )
                dialogFragment.show(parentFragmentManager, "Apartment editing")
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(requireContext(), getString(e.getResourceStringCode()))
            }
        }
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
}

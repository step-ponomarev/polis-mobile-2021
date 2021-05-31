package ru.mail.polis.ui.fragments.apartment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.forEach
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.metro.Metro
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.room.RoomCount
import ru.mail.polis.utils.StorageUtils

class EditApartmentFragment : ApartmentFragment() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var editApartmentButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_apartment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editApartmentButton = view.findViewById(R.id.edit_button)
        editApartmentButton.setOnClickListener(this::onClickEditApartment)

        val email = StorageUtils.getCurrentUserEmail(requireContext())
        scope.launch(Dispatchers.Main) {
            try {
                val apartmentED = apartmentViewModel.getApartmentByEmail(email)
                if (apartmentED != null) {
                    fillFields(apartmentED)
                } else {
                    NotificationCenter.showDefaultToast(
                        requireContext(),
                        getString(R.string.toast_there_are_no_apartment_to_edit)
                    )
                }
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.coroutineContext.cancelChildren()
    }

    private fun onClickEditApartment(view: View) {
        val selectedChip = chipGroup.findViewById<Chip>(chipGroup.checkedChipId)

        if (selectedChip == null) {
            NotificationCenter.showDefaultToast(
                requireContext(),
                getString(R.string.toast_fill_all_information_about_apartment)
            )
            return
        }

        val metro = spinner.selectedItem.toString()
        val rooms = selectedChip.text.toString()
        val cost = costEditText.text.toString()
        val square = squareEditText.text.toString()

        if (metro.isEmpty() || rooms.isBlank() || cost.isBlank() || square.isBlank()) {
            NotificationCenter.showDefaultToast(
                requireContext(),
                getString(R.string.toast_fill_all_information_about_apartment)
            )
            return
        }

        val email = StorageUtils.getCurrentUserEmail(requireContext())
        scope.launch(Dispatchers.Main) {
            try {
                val photoUrls = apartmentViewModel.getApartmentPhotoUrls()
                val apartmentED = ApartmentED(
                    email = email,
                    metro = Metro.from(metro),
                    roomCount = RoomCount.from(rooms),
                    apartmentSquare = square.toLong(),
                    apartmentCosts = cost.toLong(),
                    photosUrls = photoUrls
                )

                apartmentViewModel.updateApartment(apartmentED)
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    private fun fillFields(apartmentED: ApartmentED) {
        spinner.setSelection(Metro.values().indexOf(apartmentED.metro))

        chipGroup.forEach { view ->
            if (view is Chip) {
                if (view.text == apartmentED.roomCount.label) {
                    view.isChecked = true
                }
            }
        }

        costEditText.setText(apartmentED.apartmentCosts.toString())
        squareEditText.setText(apartmentED.apartmentSquare.toString())

        scope.launch(Dispatchers.Main) {
            for (url in apartmentED.photosUrls) {
                val drawable = withContext(Dispatchers.IO) {
                    Glide.with(requireContext()).load(url).submit().get()
                }

                photoLinearLayout.addView(createImageComponent(drawable.toBitmap()))
            }
        }
    }
}

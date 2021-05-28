package ru.mail.polis.ui.fragments.apartment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import ru.mail.polis.R
import ru.mail.polis.decoder.DecoderFactory
import ru.mail.polis.metro.Metro
import ru.mail.polis.ui.fragments.FragmentUtils
import ru.mail.polis.ui.fragments.LayoutSettings
import ru.mail.polis.viewModels.ApartmentViewModel

open class ApartmentFragment : Fragment() {

    companion object {
        const val PHOTO_CONSTRAINT_LAYOUT_WIDTH = 200
        const val PHOTO_CONSTRAINT_LAYOUT_HEIGHT = 200
    }

    protected lateinit var spinner: Spinner
    protected lateinit var metroCircleIv: ImageView
    protected lateinit var chipGroup: ChipGroup
    protected lateinit var costEditText: EditText
    protected lateinit var squareEditText: EditText
    protected lateinit var addPhotoImageButton: ImageButton
    protected lateinit var photoLinearLayout: LinearLayout
    protected lateinit var apartmentViewModel: ApartmentViewModel
    private val metroList = Metro.values()

    private val takePhotoFromGallery =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResult
        )

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (apartmentViewModel.list.isNotEmpty()) {
            apartmentViewModel.list.forEach { bitmap ->
                photoLinearLayout.addView(createImageComponent(bitmap))
            }
        }
    }

    fun onClickAddPhoto(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        takePhotoFromGallery.launch(intent)
    }

    private fun handleResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {

            val selectedImage: Uri? = result.data?.data

            val bitmap = DecoderFactory.getImageDecoder(requireContext().contentResolver)
                .decode(selectedImage!!)

            photoLinearLayout.addView(createImageComponent(bitmap))
        }
    }

    fun initSpinner(view: View) {
        spinner = view.findViewById(R.id.component_apartment_info__spinner)

        val metroNamesList = metroList.map { it.stationName }

        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, metroNamesList)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = it
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val metro = Metro.values()[position]
                if (view != null) {
                    metroCircleIv.background.setTint(
                        ContextCompat.getColor(
                            view.context,
                            metro.branchColor
                        )
                    )
                }
            }
        }
    }

    fun createImageComponent(bitmap: Bitmap): ConstraintLayout {

        val view: View = LayoutInflater.from(context).inflate(R.layout.component_photo, null)

        val cl = view.findViewById<ConstraintLayout>(R.id.photo_component__cl)
        val iv = view.findViewById<ImageView>(R.id.photo_component__iv)
        val ib = view.findViewById<ImageButton>(R.id.photo_component__ib)

        ib.setOnClickListener {
            apartmentViewModel.list.remove(iv.drawable.toBitmap())
            photoLinearLayout.removeView(cl)
        }

        iv.setOnClickListener {
            val bundle = Bundle()

            val view = it as ImageView
            bundle.putParcelable(FragmentUtils.BUNDLE_IMAGE_KEY, view.drawable.toBitmap())

            findNavController().navigate(R.id.nav_graph__full_image_fragment, bundle)
        }

        cl.layoutParams = ViewGroup.MarginLayoutParams(
            LayoutSettings.getLayoutParams(
                PHOTO_CONSTRAINT_LAYOUT_WIDTH, PHOTO_CONSTRAINT_LAYOUT_HEIGHT
            )
        )
        cl.setPadding(10, 0, 10, 0)

        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        iv.setImageBitmap(bitmap)

        apartmentViewModel.list.add(bitmap)

        return cl
    }

    fun getEmail(): String {
        return activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )?.getString(getString(R.string.preference_email_key), null)
            ?: throw IllegalStateException("Email not found")
    }

    fun getToastWithText(text: String): Toast {
        return Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        )
    }
}

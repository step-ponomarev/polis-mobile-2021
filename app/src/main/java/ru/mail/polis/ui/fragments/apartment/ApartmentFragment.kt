package ru.mail.polis.ui.fragments.apartment
import android.app.Activity
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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import ru.mail.polis.R
import ru.mail.polis.decoder.DecoderFactory
import ru.mail.polis.metro.Metro
import ru.mail.polis.ui.fragments.FragmentUtils
import ru.mail.polis.ui.fragments.LayoutSettings
import ru.mail.polis.viewModels.ApartmentViewModel

abstract class ApartmentFragment : Fragment() {

    companion object {
        const val PHOTO_CONSTRAINT_LAYOUT_WIDTH = 200
        const val PHOTO_CONSTRAINT_LAYOUT_HEIGHT = 200
    }

    protected lateinit var spinner: Spinner
    protected lateinit var metroCircleIv: ImageView
    protected lateinit var chipGroup: ChipGroup
    protected lateinit var costEditText: EditText
    protected lateinit var squareEditText: EditText
    protected lateinit var photoLinearLayout: LinearLayout
    protected lateinit var apartmentViewModel: ApartmentViewModel

    private val metroList = Metro.values()
    private lateinit var addPhotoImageButton: ImageButton

    private val takePhotoFromGallery =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::handleResult
        )

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        apartmentViewModel.getImageList()
            .forEach { bitmap ->
                photoLinearLayout.addView(createImageComponent(bitmap))
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        metroCircleIv = view.findViewById(R.id.component_apartment_info__circle)
        costEditText = view.findViewById(R.id.component_apartment_info__set_cost_et)
        squareEditText = view.findViewById(R.id.component_apartment_info__set_squared_metres_et)
        chipGroup = view.findViewById(R.id.component_rooms__chip_group)
        addPhotoImageButton = view.findViewById(R.id.component_apartment_info__add_image_button)
        photoLinearLayout = view.findViewById(R.id.component_apartment_info__photo_linear_layout)

        apartmentViewModel = ViewModelProvider(this).get(ApartmentViewModel::class.java)
        addPhotoImageButton.setOnClickListener(this::onClickAddPhoto)
        initSpinner(view)
    }

    private fun onClickAddPhoto(view: View) {
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

    private fun initSpinner(view: View) {
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

    protected fun createImageComponent(bitmap: Bitmap): ConstraintLayout {

        val view: View = LayoutInflater.from(context).inflate(R.layout.component_photo, null)

        val constraintLayoutPhotoComponent =
            view.findViewById<ConstraintLayout>(R.id.photo_component__cl)
        val imageViewPhoto = view.findViewById<ImageView>(R.id.photo_component__iv)
        val imageButtonDeletePhoto = view.findViewById<ImageButton>(R.id.photo_component__ib)

        imageButtonDeletePhoto.setOnClickListener {
            apartmentViewModel.removeImage(imageViewPhoto.drawable.toBitmap())
            photoLinearLayout.removeView(constraintLayoutPhotoComponent)
        }

        imageViewPhoto.setOnClickListener {
            val bundle = Bundle()

            val view = it as ImageView
            bundle.putParcelable(FragmentUtils.BUNDLE_IMAGE_KEY, view.drawable.toBitmap())

            findNavController().navigate(R.id.nav_graph__full_image_fragment, bundle)
        }

        constraintLayoutPhotoComponent.layoutParams = ViewGroup.MarginLayoutParams(
            LayoutSettings.getLayoutParams(
                PHOTO_CONSTRAINT_LAYOUT_WIDTH, PHOTO_CONSTRAINT_LAYOUT_HEIGHT
            )
        )
        constraintLayoutPhotoComponent.setPadding(10, 0, 10, 0)

        imageViewPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
        imageViewPhoto.setImageBitmap(bitmap)

        apartmentViewModel.addImage(bitmap)

        return constraintLayoutPhotoComponent
    }
}

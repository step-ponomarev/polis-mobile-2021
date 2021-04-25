package ru.mail.polis.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.mail.polis.R
import ru.mail.polis.metro.Metro
import ru.mail.polis.viewModels.AddApartmentViewModel


class AddApartmentFragment : Fragment() {

    val viewModel: AddApartmentViewModel by viewModels()
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri = imageReturnedIntent.getData()
                val img: pictureView = findViewById(R.id.img) as pictureView
                img.setImageURI(selectedImage)
            }

        }

    private lateinit var spinner: Spinner
    private lateinit var addApartmentButton: Button
    private lateinit var metroCircleIv: ImageView
    private lateinit var chipGroup: ChipGroup
    private lateinit var costEditText: EditText
    private lateinit var squareEditText: EditText
    private lateinit var addPhotoImageButton: ImageButton
    private val metroList = Metro.values()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_apartment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.fragment_add_apartment_spinner)
        metroCircleIv = view.findViewById(R.id.fragment_add_apartment__circle)

        addApartmentButton = view.findViewById(R.id.add_button)
        costEditText = view.findViewById(R.id.fragment_add_apartment__set_cost_et)
        squareEditText = view.findViewById(R.id.fragment_add_apartment__set_squared_metres_et)
        chipGroup = view.findViewById(R.id.component_rooms__chip_group)
        addPhotoImageButton = view.findViewById(R.id.fragment_add_apartment__add_image_button)

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

        addApartmentButton.setOnClickListener(this::onClickAddApartment)
        addPhotoImageButton.setOnClickListener(this::onClickAddPhoto)
    }

    private fun onClickAddApartment(view: View) {

        val selectedChip = chipGroup.findViewById<Chip>(chipGroup.checkedChipId)

        val apartmentInfo = hashMapOf(
            "metro" to spinner.selectedItem.toString(),
            "rooms" to selectedChip.text.toString(),
            "cost" to costEditText.text.toString(),
            "square" to squareEditText.text.toString()
        )

        viewModel.save(apartmentInfo)

        findNavController().navigate(R.id.nav_graph__list_of_people)
    }

    private fun onClickAddPhoto(view: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startForResult.launch(intent)
    }

//    private fun handleResult(result: ActivityResult) {
//
//        var bitmap: Bitmap? = null
//        val imageView = findViewById(R.id.imageView) as ImageView
//
//        when (requestCode) {
//            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
//                val selectedImage: Uri = imageReturnedIntent.getData()
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//                imageView.setImageBitmap(bitmap)
//            }
//        }
//    }
}

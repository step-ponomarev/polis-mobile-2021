package ru.mail.polis.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.mail.polis.R
import ru.mail.polis.metro.Metro


class AddApartmentFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.P)
    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val iv = ImageView(requireContext())

            val selectedImage: Uri? = result.data?.data
            val source = ImageDecoder.createSource(requireContext().contentResolver, selectedImage!!)
            val bitmap = ImageDecoder.decodeBitmap(source)
            iv.setImageBitmap(bitmap)

            photoLinearLayout.addView(iv)
        }
    }

    private lateinit var spinner: Spinner
    private lateinit var addApartmentButton: Button
    private lateinit var metroCircleIv: ImageView
    private lateinit var chipGroup: ChipGroup
    private lateinit var costEditText: EditText
    private lateinit var squareEditText: EditText
    private lateinit var addPhotoImageButton: ImageButton
    private lateinit var photoLinearLayout: LinearLayout
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
        photoLinearLayout = view.findViewById(R.id.fragment_add_apartment__photo_linear_layout)

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

        findNavController().navigate(R.id.nav_graph__list_of_people)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun onClickAddPhoto(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        takePicture.launch(intent)
    }
}

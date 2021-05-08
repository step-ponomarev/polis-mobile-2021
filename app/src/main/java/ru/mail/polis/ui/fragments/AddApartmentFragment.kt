package ru.mail.polis.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.mail.polis.R
import ru.mail.polis.metro.Metro
import ru.mail.polis.viewModels.AddApartmentViewModel
import ru.mail.polis.viewModels.StateScrollView


class AddApartmentFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var addApartmentButton: Button
    private lateinit var metroCircleIv: ImageView
    private lateinit var chipGroup: ChipGroup
    private lateinit var costEditText: EditText
    private lateinit var squareEditText: EditText
    private lateinit var addPhotoImageButton: ImageButton
    private lateinit var photoLinearLayout: LinearLayout
    private lateinit var addApartmentViewModel: AddApartmentViewModel
    private val metroList = Metro.values()

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
        return inflater.inflate(R.layout.fragment_add_apartment, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)


        if (StateScrollView.list.isNotEmpty()) {
            StateScrollView.list.forEach { bitmap ->
                photoLinearLayout.addView(createImageComponent(bitmap))
            }
        }

//        StateScrollView.list.clear()
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

        addApartmentViewModel = ViewModelProvider(this).get(AddApartmentViewModel::class.java)

        addApartmentViewModel.liveData.observe(viewLifecycleOwner, Observer {
            photoLinearLayout = it
        })

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

        val selectedChip = chipGroup.findViewById<Chip>(chipGroup.checkedChipId) ?: return

        val apartmentInfo = hashMapOf(
            "metro" to spinner.selectedItem.toString(),
            "rooms" to selectedChip.text.toString(),
            "cost" to costEditText.text.toString(),
            "square" to squareEditText.text.toString()
        )

        findNavController().navigate(R.id.nav_graph__list_of_people)
    }

    private fun onClickAddPhoto(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        takePhotoFromGallery.launch(intent)
    }

    private fun handleResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {

            val selectedImage: Uri? = result.data?.data

            val bitmap = decodeImage(selectedImage)

            photoLinearLayout.addView(createImageComponent(bitmap))

//            addApartmentViewModel.addView(createImageComponent(bitmap), photoLinearLayout)
            Log.d("!!!", "FROM HADLE RESULT CHILD NUBMER : ${photoLinearLayout.childCount}")
        }
    }

    private fun createImageComponent(bitmap: Bitmap): ConstraintLayout {

        val view: View = LayoutInflater.from(context).inflate(R.layout.component_photo, null)

        val cl = view.findViewById<ConstraintLayout>(R.id.photo_component__cl)
        val iv = view.findViewById<ImageView>(R.id.photo_component__iv)
        val ib = view.findViewById<ImageButton>(R.id.photo_component__ib)

        ib.setOnClickListener {
            val parent = it.parent
            val linearLayout = parent.parent as ViewGroup
            StateScrollView.list.remove(iv.drawable.toBitmap())
            linearLayout.removeViewInLayout(view)
        }

        iv.setOnClickListener {
            val bundle = Bundle()

            val view = it as ImageView
            bundle.putParcelable("image", view.drawable.toBitmap())

            findNavController().navigate(R.id.nav_graph__full_image_fragment, bundle)
        }

        cl.layoutParams = ViewGroup.MarginLayoutParams(getLayoutParams(200, 200))
        cl.setPadding(10, 0, 10, 0)

        iv.scaleType = ImageView.ScaleType.CENTER_CROP

        iv.setImageBitmap(bitmap)
        StateScrollView.list.add(bitmap)

        return cl
    }

    private fun getLayoutParams(width: Int, height: Int): ViewGroup.LayoutParams {
        val prm: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        prm.width = width
        prm.height = height

        return prm
    }

    private fun decodeImage(selectedImage: Uri?): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val image =
                ImageDecoder.createSource(requireContext().contentResolver, selectedImage!!)
            ImageDecoder.decodeBitmap(image)
        } else {
            val imageStream =
                requireActivity().contentResolver.openInputStream(selectedImage!!)
            BitmapFactory.decodeStream(imageStream)
        }
    }
}

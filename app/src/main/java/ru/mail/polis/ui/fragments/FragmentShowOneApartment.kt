package ru.mail.polis.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import ru.mail.polis.R
import ru.mail.polis.list.of.apartments.ApartmentView

class FragmentShowOneApartment : Fragment() {

    private lateinit var showPhoneButton: Button
    private lateinit var apartment: ApartmentView
    private lateinit var userAvatar: ImageView
    private lateinit var apartmentOwnerName: TextView
    private lateinit var apartmentOwnerAge: TextView
    private lateinit var metroText: TextView
    private lateinit var roomCount: Chip
    private lateinit var metroBranchColor: ImageView
    private lateinit var apartmentSquare: TextView
    private lateinit var apartmentCost: TextView
    private lateinit var photoContainer: LinearLayout
    private lateinit var phoneText: TextView
    private lateinit var rejectButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_one_apartment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: FragmentShowOneApartmentArgs by navArgs()
        apartment = args.apartment
        rejectButton = view.findViewById(R.id.fragment_show_one_apartment__reject)
        showPhoneButton = view.findViewById(R.id.fragment_show_one_apartment__btn_show_phone)
        userAvatar = view.findViewById(R.id.component_person_header__avatar)
        apartmentOwnerName = view.findViewById(R.id.component_person_header__name)
        apartmentOwnerAge = view.findViewById(R.id.component_person_header__age)
        metroText = view.findViewById(R.id.component_proposed_apartment_item__metro_text)
        roomCount = view.findViewById(R.id.component_proposed_apartment_item__room_count_chip)
        metroBranchColor =
            view.findViewById(R.id.component_proposed_apartment_item__metro_branch_color)
        apartmentSquare = view.findViewById(R.id.component_proposed_apartment_item__square_text)
        apartmentCost = view.findViewById(R.id.component_proposed_apartment_item__cost_text)
        photoContainer = view.findViewById(R.id.component_proposed_apartment_item__photos_container)
        phoneText = view.findViewById(R.id.fragment_show_one_apartment__phone)

        if (apartment.ownerAvatar != null) {
            Glide.with(view).load(apartment.ownerAvatar).into(userAvatar)
        } else {
            userAvatar.setImageResource(R.drawable.stub_person_avatar)
        }

        metroBranchColor.background.setTint(
            ContextCompat.getColor(
                view.context,
                apartment.metro.branchColor
            )
        )

        apartmentOwnerName.text = apartment.ownerName
        apartmentOwnerAge.text = "${apartment.ownerAge} лет"
        metroText.text = apartment.metro.stationName
        roomCount.text = apartment.roomCount.label
        apartmentSquare.text = "${apartment.apartmentSquare} м. кв."
        apartmentCost.text = "${apartment.apartmentCosts} Р"

        val photos: List<ImageView> = apartment.photosUrls.map { url ->
            urlToImageView(view.context, url)
        }

        photos.forEach(photoContainer::addView)

        showPhoneButton.setOnClickListener {
            phoneText.text = apartment.phone
            it.visibility = View.INVISIBLE
            rejectButton.visibility = View.INVISIBLE
            phoneText.visibility = View.VISIBLE
        }

    }

    private fun urlToImageView(context: Context, url: String): ImageView {
        val iv = ImageView(context)

        iv.layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )

        iv.adjustViewBounds = true
        iv.setPadding(5, 0, 5, 0)

        return Glide.with(iv).load(url).into(iv).view
    }
}

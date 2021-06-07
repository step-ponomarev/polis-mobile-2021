package ru.mail.polis.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.list.of.apartments.ApartmentView
import ru.mail.polis.list.of.propose.ProposeView
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.ShowOneApartmentViewModel

class FragmentShowOneApartment : Fragment() {

    private lateinit var showPhoneButton: Button
    private lateinit var apartment: ApartmentView
    private lateinit var propose: ProposeView
    private lateinit var userAvatar: ImageView
    private lateinit var apartmentOwnerName: TextView
    private lateinit var apartmentOwnerAge: TextView
    private lateinit var metroText: TextView
    private lateinit var roomCount: Chip
    private lateinit var metroBranchColor: ImageView
    private lateinit var apartmentSquare: TextView
    private lateinit var apartmentCost: TextView
    private lateinit var photoContainer: LinearLayout
    private lateinit var phoneContainer: LinearLayout
    private lateinit var rejectButton: Button
    private lateinit var phoneButton: Button
    private var viewModel = ShowOneApartmentViewModel()
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
        propose = args.propose
        phoneContainer = view.findViewById(R.id.fragment_show_one_apartment__ll_phone)
        rejectButton = view.findViewById(R.id.fragment_show_one_apartment__reject)
        showPhoneButton = view.findViewById(R.id.fragment_show_one_apartment__btn_show_phone)
        phoneButton = view.findViewById(R.id.fragment_show_one_apartment__phone)
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

        if (propose.status == ProposeStatus.ACCEPTED) {
            phoneButton.text = apartment.phone
            phoneContainer.visibility = View.VISIBLE
            rejectButton.visibility = View.VISIBLE
        } else {
            showPhoneButton.visibility = View.VISIBLE
            rejectButton.visibility = View.VISIBLE
        }

        phoneButton.setOnClickListener {
            val number = Uri.parse("tel:" + apartment.phone)
            val callIntent: Intent = Intent(Intent.ACTION_DIAL, number)

            val packageManager: PackageManager = requireActivity().packageManager
            val activities = packageManager.queryIntentActivities(callIntent, 0)
            val isIntentSafe = activities.size > 0
            if (isIntentSafe) {
                startActivity(callIntent)
            }
        }

        showPhoneButton.setOnClickListener {
            phoneButton.text = apartment.phone
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    viewModel.updateProposeED(
                        ProposeED(
                            apartment.email,
                            StorageUtils.getCurrentUserEmail(requireContext()),
                            ProposeStatus.ACCEPTED
                        )
                    )
                } catch (e: NotificationKeeperException) {
                    NotificationCenter.showDefaultToast(
                        requireContext(),
                        getString(e.getResourceStringCode())
                    )
                }
            }
            it.visibility = View.INVISIBLE
            phoneContainer.visibility = View.VISIBLE
        }

        rejectButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    viewModel.updateProposeED(
                        ProposeED(
                            apartment.email,
                            StorageUtils.getCurrentUserEmail(requireContext()),
                            ProposeStatus.REJECTED
                        )
                    )
                } catch (e: NotificationKeeperException) {
                    NotificationCenter.showDefaultToast(
                        requireContext(),
                        getString(e.getResourceStringCode())
                    )
                }
            }
            findNavController().navigate(R.id.nav_graph__proposed_apartments_fragment)
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

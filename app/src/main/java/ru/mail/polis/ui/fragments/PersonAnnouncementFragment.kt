package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.helpers.getAgeString
import ru.mail.polis.list.of.people.PersonView
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.tags.Tags
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.PersonAnnouncementViewModel

class PersonAnnouncementFragment : Fragment() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var personView: PersonView
    private lateinit var offerApartmentButton: Button
    private lateinit var viewModel: PersonAnnouncementViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        offerApartmentButton =
            view.findViewById(R.id.fragment_person_announcement__button_show_number)
        val ivPhoto: ImageView = view.findViewById(R.id.component_person_header__avatar)
        val tvName: TextView = view.findViewById(R.id.component_person_header__name)
        val tvAge: TextView = view.findViewById(R.id.component_person_header__age)
        val llIvTags: LinearLayout = view.findViewById(R.id.fragment_person_announcement__ll_tags)
        val tvMetro: TextView = view.findViewById(R.id.fragment_person_announcement__metro_text)
        val ivBranchColor: ImageView =
            view.findViewById(R.id.fragment_person_announcement__metro_branch_color)
        val tvMoney: TextView = view.findViewById(R.id.fragment_person_announcement__tv_money)
        val tagBottomLineDivider: View =
            view.findViewById(R.id.fragment_person_announcement__tag_line_divider)
        viewModel = ViewModelProvider(this).get(PersonAnnouncementViewModel::class.java)

        val cvRooms: List<CardView> = listOf(
            view.findViewById(R.id.fragment_person_announcement__ll_cv_rooms1),
            view.findViewById(R.id.fragment_person_announcement__ll_cv_rooms2),
            view.findViewById(R.id.fragment_person_announcement__ll_cv_rooms3)
        )
        val tvRooms: List<TextView> = listOf(
            view.findViewById(R.id.fragment_person_announcement__ll_tv_rooms1),
            view.findViewById(R.id.fragment_person_announcement__ll_tv_rooms2),
            view.findViewById(R.id.fragment_person_announcement__ll_tv_rooms3)
        )
        val tvDescription: TextView =
            view.findViewById(R.id.fragment_person_announcement__tv_description)

        super.onViewCreated(view, savedInstanceState)
        val args: PersonAnnouncementFragmentArgs by navArgs()
        personView = args.person
        if (personView.photo != null) {
            urlToMyImageView(ivPhoto, personView.photo!!)
        }
        tvName.text = personView.name
        tvAge.text = getAgeString(personView.age)

        val tags: List<ImageView> = personView.tags.map { tag ->
            tagToImageView(tag)
        }

        if (tags.isEmpty()) {
            tagBottomLineDivider.visibility = View.GONE
        } else {
            tags.forEach(llIvTags::addView)
        }

        tvMetro.text = personView.metro.stationName
        ivBranchColor.background.setTint(
            ContextCompat.getColor(
                view.context,
                personView.metro.branchColor
            )
        )

        if (personView.moneyFrom == 0L && personView.moneyTo == 0L) {
            tvMoney.setText(R.string.money_default_value)
        } else {
            tvMoney.text =
                view.context.getString(R.string.money, personView.moneyFrom, personView.moneyTo)
        }

        for (i in 0..3.coerceAtMost(personView.rooms.size - 1)) {
            cvRooms[i].visibility = View.VISIBLE
            tvRooms[i].text = personView.rooms[i].label
        }
        tvDescription.text = personView.description

        offerApartmentButton.setOnClickListener(this::onOfferApartment)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.coroutineContext.cancelChildren()
    }

    private fun onOfferApartment(view: View) {
        scope.launch(Dispatchers.Main) {
            try {
                val emailPerson: String = personView.email
                viewModel.offerApartment(
                    StorageUtils.getCurrentUserEmail(requireContext()),
                    emailPerson
                )

                NotificationCenter.showDefaultToast(
                    requireContext(),
                    "Вы предложили квартиру человеку с именем ${personView.name}"
                )
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    private fun tagToImageView(tag: Tags): ImageView {
        val iv = ImageView(view?.context)

        val marginLayoutParams = ViewGroup.MarginLayoutParams(45, 45)
        marginLayoutParams.setMargins(0, 0, 20, 0)

        iv.layoutParams = marginLayoutParams
        iv.adjustViewBounds = true
        iv.setImageResource(tag.activeImage)

        return iv
    }

    private fun urlToMyImageView(iv: ImageView, url: String) {
        Glide.with(iv)
            .load(url)
            .into(iv)
    }
}

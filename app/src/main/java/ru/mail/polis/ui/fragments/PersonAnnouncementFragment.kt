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
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.helpers.getAgeString
import ru.mail.polis.list.of.people.PersonView
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.room.RoomCount
import ru.mail.polis.tags.Tags
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.PersonAnnouncementViewModel

class PersonAnnouncementFragment : Fragment() {
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
        val tvMetres: TextView = view.findViewById(R.id.fragment_person_announcement__tv_metres)
        val tagBottomLineDivider: View =
            view.findViewById(R.id.fragment_person_announcement__tag_line_divider)
        viewModel = ViewModelProvider(this).get(PersonAnnouncementViewModel::class.java)
        val roomLinearLayout: LinearLayout =
            view.findViewById(R.id.fragment_person_announcement__ll_rooms)

        val tvDescription: TextView =
            view.findViewById(R.id.fragment_person_announcement__tv_description)

        super.onViewCreated(view, savedInstanceState)
        val args: PersonAnnouncementFragmentArgs by navArgs()
        personView = args.person
        if (personView.photo != null) {
            urlToMyImageView(ivPhoto, personView.photo!!)
        }
        tvName.text = personView.name
        if (personView.age != null) {
            tvAge.text = getAgeString(personView.age!!, requireContext())
        }

        val tags: List<ImageView> = personView.tags.map { tag ->
            tagToImageView(tag)
        }

        if (tags.isEmpty()) {
            tagBottomLineDivider.visibility = View.GONE
        } else {
            tags.forEach(llIvTags::addView)
        }

        if (personView.metro != null) {
            tvMetro.text = personView.metro!!.stationName
            ivBranchColor.background.setTint(
                ContextCompat.getColor(
                    view.context,
                    personView.metro!!.branchColor
                )
            )
        }

        if (personView.moneyFrom == 0L && personView.moneyTo == 0L) {
            tvMoney.setText(R.string.money_default_value)
        } else {
            tvMoney.text =
                view.context.getString(R.string.money, personView.moneyFrom, personView.moneyTo)
        }

        if (personView.metresFrom == 0L && personView.metresTo == 0L) {
            tvMetres.setText(R.string.metres_default_value)
        } else {
            tvMetres.text =
                view.context.getString(R.string.metres, personView.metresFrom, personView.metresTo)
        }

        personView.rooms.forEach { room ->
            val cardView = createRoomCardView(requireContext(), room, roomLinearLayout)

            roomLinearLayout.addView(cardView)
        }

        tvDescription.text = personView.description

        offerApartmentButton.setOnClickListener(this::onOfferApartment)
    }

    private fun createRoomCardView(
        context: Context,
        room: RoomCount,
        root: ViewGroup
    ): CardView {
        val view: View =
            LayoutInflater.from(context)
                .inflate(R.layout.component_card_view_room, root, false)
        val cardView: CardView = view.findViewById(R.id.component_card_view_room__card_view)
        val textView: TextView = view.findViewById(R.id.component_card_view_room__text_view)

        textView.text = room.label
        return cardView
    }

    private fun onOfferApartment(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val emailPerson: String = personView.email
                    ?: throw IllegalStateException(
                        "Advert with email ${personView.email} is not exist"
                    )

                if (emailPerson.compareTo(StorageUtils.getCurrentUserEmail(requireContext())) == 0) {
                    NotificationCenter.showDefaultToast(
                        requireContext(),
                        "Вы не можете предложить квартиру самому себе"
                    )

                    return@launch
                }

                viewModel.offerApartment(
                    StorageUtils.getCurrentUserEmail(requireContext()),
                    emailPerson
                )

                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(R.string.text_you_offer_apartment_to) + personView.name
                )
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    "У вас не добавлена квартира"
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

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
import ru.mail.polis.exception.NotificationKeeperException
import ru.mail.polis.helpers.getAgeString
import ru.mail.polis.list.of.people.Person
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.viewModels.PersonAnnouncementViewModel

class PersonAnnouncementFragment : Fragment() {
    private lateinit var person: Person
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
        person = args.person
        if (person.photo != null) {
            urlToMyImageView(ivPhoto, person.photo!!)
        }
        tvName.text = person.name
        if (person.age != null)
            tvAge.text = getAgeString(person.age!!)

        val tags: List<ImageView> = person.tags.map { url ->
            urlToImageView(view.context, url)
        }
        tags.forEach(llIvTags::addView)

        if (person.metro != null) {
            tvMetro.text = person.metro!!.stationName
            ivBranchColor.background.setTint(
                ContextCompat.getColor(
                    view.context,
                    person.metro!!.branchColor
                )
            )
        }

        if (person.moneyFrom == 0L && person.moneyTo == 0L) {
            tvMoney.setText(R.string.money_default_value)
        } else {
            tvMoney.text = view.context.getString(R.string.money, person.moneyFrom, person.moneyTo)
        }

        for (i in 0..3.coerceAtMost(person.rooms.size - 1)) {
            cvRooms[i].visibility = View.VISIBLE
            tvRooms[i].text = person.rooms[i].label
        }
        tvDescription.text = person.description

        offerApartmentButton.setOnClickListener(this::onOfferApartment)
    }

    private fun onOfferApartment(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val emailPerson: String = person.email
                    ?: throw IllegalStateException("Advert is not exist")

                viewModel.offerApartment(
                    getEmail(),
                    emailPerson
                )

                NotificationCenter.showDefaultToast(
                    requireContext(),
                    "Вы предложили квартиру человеку с именем ${person.name}"
                )
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    private fun urlToImageView(context: Context, url: Long): ImageView {
        val iv = ImageView(context)

        iv.layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        iv.adjustViewBounds = true
        iv.setPadding(5, 5, 10, 5)

        Glide.with(iv)
            .load(url)
            .into(iv)

        return iv
    }

    private fun urlToMyImageView(iv: ImageView, url: String) {
        Glide.with(iv)
            .load(url)
            .into(iv)
    }

    private fun getEmail(): String {
        return activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )?.getString(getString(R.string.preference_email_key), null)
            ?: throw IllegalStateException("Email not found")
    }
}

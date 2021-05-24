package ru.mail.polis.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.mail.polis.R
import ru.mail.polis.helpers.getAgeString

class PersonAnnouncementFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ivPhoto: ImageView = view.findViewById(R.id.component_person_header__avatar)
        val tvName: TextView = view.findViewById(R.id.component_person_header__name)
        val tvAge: TextView = view.findViewById(R.id.component_person_header__age)
        val llIvTags: LinearLayout = view.findViewById(R.id.fragment_person_announcement__ll_tags)
        val tvMetro: TextView = view.findViewById(R.id.fragment_person_announcement__metro_text)
        val ivBranchColor: ImageView = view.findViewById(R.id.fragment_person_announcement__metro_branch_color)
        val tvMoney: TextView = view.findViewById(R.id.fragment_person_announcement__tv_money)
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
        val person = args.person
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
            tvRooms[i].text = person.rooms[i]
        }
        tvDescription.text = person.description
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
}

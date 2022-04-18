package ru.mail.polis.ui.fragments.advert

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import ru.mail.polis.R
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.metro.Metro
import ru.mail.polis.tags.Tags
import ru.mail.polis.viewModels.AdvertViewModel

abstract class AdvertFragment : Fragment() {
    protected val viewModel = AdvertViewModel()

    protected lateinit var user: UserED
    protected lateinit var spinner: Spinner
    protected lateinit var chipGroup: ChipGroup
    protected lateinit var avatarImageView: ImageView
    protected lateinit var nameTextView: TextView
    protected lateinit var ageTextView: TextView
    protected lateinit var costFromEditText: EditText
    protected lateinit var costToEditText: EditText
    protected lateinit var metresFromEditText: EditText
    protected lateinit var metresToEditText: EditText
    protected lateinit var aboutMeEditText: EditText
    protected lateinit var llTags: LinearLayout
    protected var tagsForPerson: MutableList<Tags> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.fragment_advert_creation__spinner)
        val metroNamesList = Metro.values().map { it.stationName }
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, metroNamesList)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = it
            }

        chipGroup = view.findViewById(R.id.component_rooms__chip_group)
        costFromEditText = view.findViewById(R.id.fragment_advert_creation__from_price_et)
        costToEditText = view.findViewById(R.id.fragment_advert_creation__to_price_et)
        metresFromEditText = view.findViewById(R.id.fragment_advert_creation__from_metres_et)
        metresToEditText= view.findViewById(R.id.fragment_advert_creation__to_metres_et)
        aboutMeEditText = view.findViewById(R.id.fragment_advert_creation__about_me_et)
        avatarImageView = view.findViewById(R.id.component_person_header__avatar)
        nameTextView = view.findViewById(R.id.component_person_header__name)
        ageTextView = view.findViewById(R.id.component_person_header__age)

        chipGroup.isSingleSelection = false

        llTags = view.findViewById(R.id.fragment_advert_creation__ll_tags)
    }

    protected fun tagToImageButton(tag: Tags, defaultTagImage: Boolean = true): ImageButton {
        val ib = ImageButton(requireContext())
        ib.layoutParams = ViewGroup.LayoutParams(
            60,
            60
        )
        ib.adjustViewBounds = true
        ib.background = null
        ib.setPadding(5, 5, 5, 5)

        if (!defaultTagImage) {
            ib.setImageResource(tag.activeImage)
            tagsForPerson.add(tag)
        } else {
            ib.setImageResource(tag.defaultImage)
        }

        ib.setOnClickListener {
            if (tag in tagsForPerson) {
                ib.setImageResource(tag.defaultImage)
                tagsForPerson.remove(tag)
            } else {
                ib.setImageResource(tag.activeImage)
                tagsForPerson.add(tag)
            }
        }
        return ib
    }
}

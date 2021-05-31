package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.metro.Metro
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.room.RoomCount
import ru.mail.polis.tags.Tags
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.AdvertCreationViewModel
import java.util.Collections
import ru.mail.polis.dao.DaoResult

class AdvertCreationFragment : Fragment() {
    private val viewModel = AdvertCreationViewModel()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var email: String
    private lateinit var spinner: Spinner
    private lateinit var chipGroup: ChipGroup
    private lateinit var avatarImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var costFromEditText: EditText
    private lateinit var costToEditText: EditText
    private lateinit var aboutMeEditText: EditText
    private lateinit var createAdvertFragment: Button
    private lateinit var llTags: LinearLayout
    private var tagsForPerson: MutableList<Tags> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advert_creation, container, false)
    }

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
        aboutMeEditText = view.findViewById(R.id.fragment_advert_creation__about_me_et)
        createAdvertFragment = view.findViewById(R.id.fragment_advert_creation__continue_btn)
        avatarImageView = view.findViewById(R.id.component_person_header__avatar)
        nameTextView = view.findViewById(R.id.component_person_header__name)
        ageTextView = view.findViewById(R.id.component_person_header__age)
        llTags = view.findViewById(R.id.fragment_advert_creation__ll_tags)

        val tags: List<ImageView> = Tags.values().map { tag ->
            tagToImageButton(tag)
        }
        tags.forEach(llTags::addView)

        email = StorageUtils.getCurrentUserEmail(requireContext())

        viewModel.fetchUser(email)
        viewModel.useResult
            .onEach {
                when (it) {
                    is DaoResult.Success -> {
                        val user = it.data
                            ?: //TODO: Сообщить юзеру что что-то не так
                            return@onEach

                        if (user.photo != null) {
                            Glide.with(avatarImageView).load(user.photo).into(avatarImageView)
                        }

                        nameTextView.text = "${user.name} ${user.surname}"
                        ageTextView.text = user.age.toString()
                    }
                    is DaoResult.Error -> {
                        //TODO: Сообщить юзеру что что-то не так
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        createAdvertFragment.setOnClickListener(this::createAdvert)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.coroutineContext.cancelChildren()
    }

    private fun createAdvert(view: View) {
        val selectedChip = chipGroup.findViewById<Chip>(chipGroup.checkedChipId)
        if (selectedChip == null) {

            NotificationCenter.showDefaultToast(
                requireContext(),
                getString(R.string.toast_fill_all_advert_info)
            )
            return
        }

        val metro = spinner.selectedItem.toString()
        val roomCount = selectedChip.text.toString()
        val costFrom = costFromEditText.text.toString()
        val costTo = costToEditText.text.toString()
        val aboutMe = aboutMeEditText.text.toString()

        if (metro.isBlank() ||
            roomCount.isBlank() ||
            costFrom.isBlank() ||
            costTo.isBlank()
        ) {
            NotificationCenter.showDefaultToast(
                requireContext(),
                getString(R.string.toast_fill_all_advert_info)
            )
            return
        }

        scope.launch(Dispatchers.Main) {
            val person = PersonED(
                email = email,
                metro = Metro.from(metro),
                description = aboutMe,
                moneyFrom = costFrom.toLong(),
                moneyTo = costTo.toLong(),
                rooms = Collections.singletonList(RoomCount.from(roomCount)),
                tags = tagsForPerson
            )

            try {
                viewModel.addPerson(person)
                findNavController().navigate(R.id.nav_graph__list_of_people)
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    private fun tagToImageButton(tag: Tags): ImageButton {
        val ib = ImageButton(requireContext())
        ib.layoutParams = ViewGroup.LayoutParams(
            60,
            60
        )
        ib.adjustViewBounds = true
        ib.background = null
        ib.setPadding(5, 5, 5, 5)
        ib.setImageResource(tag.defaultImage)
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

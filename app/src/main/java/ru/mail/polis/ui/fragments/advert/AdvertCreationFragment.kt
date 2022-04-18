package ru.mail.polis.ui.fragments.advert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.exceptions.InvalidRangeException
import ru.mail.polis.metro.Metro
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.room.RoomCount
import ru.mail.polis.tags.Tags
import ru.mail.polis.utils.StorageUtils

class AdvertCreationFragment : AdvertFragment() {

    private lateinit var addAdvertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advert_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAdvertButton = view.findViewById(R.id.fragment_advert_creation__continue_btn)

        val email = StorageUtils.getCurrentUserEmail(requireContext())
        GlobalScope.launch(Dispatchers.Main) {
            try {
                user = viewModel.fetchUser(email)
                    ?: throw IllegalStateException("User not found by email: $email")

                if (user.photo != null) {
                    Glide.with(avatarImageView).load(user.photo).into(avatarImageView)
                }

                nameTextView.text = "${user.name} ${user.surname}"
                ageTextView.text = user.age.toString()
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }

        val tags: List<ImageView> = Tags.values().map { tag ->
            tagToImageButton(tag)
        }
        tags.forEach(llTags::addView)

        addAdvertButton.setOnClickListener(this::onClickAddAdvert)
    }

    private fun onClickAddAdvert(view: View) {
        try {

            val checkedChips = chipGroup.checkedChipIds

            val roomList = mutableListOf<RoomCount>()

            checkedChips.forEach { chipId ->
                val selectedChip = chipGroup.findViewById<Chip>(chipId)
                roomList.add(RoomCount.from(selectedChip.text.toString()))
            }

            val metro = spinner.selectedItem.toString()
            val costFrom = costFromEditText.text.toString()
            val costTo = costToEditText.text.toString()
            val metresFrom = metresFromEditText.text.toString()
            val metresTo = metresToEditText.text.toString()
            val aboutMe = aboutMeEditText.text.toString()


            if (metro.isBlank() ||
                roomList.isEmpty() ||
                costFrom.isBlank() ||
                costTo.isBlank() ||
                metresFrom.isBlank() ||
                metresTo.isBlank()
            ) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(R.string.toast_fill_all_advert_info)
                )
                return
            }

            if (costFrom.toInt() >= costTo.toInt()) {
                throw InvalidRangeException("Первое число диапазона цен должно быть меньше второго")
            }

            if (metresFrom.toInt() >= metresTo.toInt()) {
                throw InvalidRangeException("Первое число диапазона метров должно быть меньше второго")
            }

            val email = StorageUtils.getCurrentUserEmail(requireContext())
            GlobalScope.launch(Dispatchers.Main) {
                val person = PersonED.Builder.createBuilder()
                    .email(email)
                    .metro(Metro.from(metro))
                    .description(aboutMe)
                    .money(costFrom.toLong(), costTo.toLong())
                    .metres(metresFrom.toLong(), metresTo.toLong())
                    .rooms(roomList)
                    .tags(tagsForPerson)
                    .build()

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
        } catch (e: InvalidRangeException) {
            NotificationCenter.showDefaultToast(
                requireContext(),
                e.message ?: return
            )
        }
    }
}

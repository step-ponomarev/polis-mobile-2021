package ru.mail.polis.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.*
import ru.mail.polis.R
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount
import java.util.*

class AdvertCreationFragment : Fragment() {
    private val personService: IPersonService = PersonService.getInstance()

    private lateinit var spinner: Spinner
    private lateinit var chipGroup: ChipGroup
    private lateinit var costFromEditText: EditText
    private lateinit var costToEditText: EditText
    private lateinit var aboutMeEditText: EditText
    private lateinit var createAdvertFragment: Button

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
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, metroNamesList)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = it
            }

        chipGroup = view.findViewById(R.id.component_rooms__chip_group)
        costFromEditText = view.findViewById(R.id.fragment_advert_creation__from_price_et)
        costToEditText = view.findViewById(R.id.fragment_advert_creation__to_price_et)
        aboutMeEditText = view.findViewById(R.id.fragment_advert_creation__about_me_et)
        createAdvertFragment = view.findViewById(R.id.fragment_advert_creation__continue_btn)

        createAdvertFragment.setOnClickListener(this::createAdvert)
    }

    private fun createAdvert(view: View) {
        val selectedChip = chipGroup.findViewById<Chip>(chipGroup.checkedChipId)
        if (selectedChip == null) {
            getToastAboutFillAllFields().show()
            return
        }

        val email = getEmail();
        val metro = Metro.from(spinner.selectedItem.toString())
        val roomCount = RoomCount.from(selectedChip.text.toString())
        val costFrom = costFromEditText.text.toString()
        val costTo = costToEditText.text.toString()
        val aboutMe = aboutMeEditText.text.toString()

        //TODO: Запросить юзера
        val person = PersonED.Builder.createBuilder()
            .email(email)
            .age(0)
            .name("NO_NAME")
            .metro(metro)
            .description(aboutMe)
            .money(costFrom.toLong(), costTo.toLong())
            .rooms(Collections.singletonList(roomCount))
            .tags(emptyList())
            .build()

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                personService.addPerson(person);
            }

            findNavController().navigate(R.id.nav_graph__list_of_people)
        }
    }

    private fun getToastAboutFillAllFields(): Toast {
        return Toast.makeText(
            requireContext(),
            getString(R.string.toast_fill_all_advert_info),
            Toast.LENGTH_SHORT
        )
    }

    private fun getEmail(): String {
        return activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )?.getString(getString(R.string.preference_email_key), null)
            ?: throw IllegalStateException("Email not found")
    }
}

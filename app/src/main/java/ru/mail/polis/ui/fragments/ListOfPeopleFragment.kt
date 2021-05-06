package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import ru.mail.polis.R
import ru.mail.polis.dao.IPersonService
import ru.mail.polis.dao.PersonED
import ru.mail.polis.dao.PersonService
import ru.mail.polis.list.of.people.PeopleAdapter
import ru.mail.polis.metro.Metro

class ListOfPeopleFragment : Fragment(), PeopleAdapter.ListItemClickListener {
    private val personService: IPersonService = PersonService.getInstance()
    private lateinit var listOfPeopleED: List<PersonED>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_of_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        runBlocking {
            listOfPeopleED = personService.findAll()
        }
        super.onViewCreated(view, savedInstanceState)
        val rvList: RecyclerView = view.findViewById(R.id.list_of_people__rv_list)
        val adapter = PeopleAdapter(listOfPeopleED, this)
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.adapter = adapter
    }

    override fun onListItemClick(clickedItemIndex: Int) {
        val person: PersonED = listOfPeopleED[clickedItemIndex]
        val action = ListOfPeopleFragmentDirections.actionNavGraphListOfPeopleToPersonAnnouncementFragment(person)
        findNavController().navigate(action) // R.id.nav_graf_task_fragment
    }
}

private fun generateTestPeopleList(): List<PersonED> {
    return listOf(
        PersonED(
            "",
            null,
            "Степан Пономарев",
            22,
            null,
            listOf(),
            Metro.KUPCHINO,
            Pair(20000, 35000),
            listOf("1 комната", "2 комнаты"),
            "Привет, меня зовут Степа и я не алкоголик. У меня есть ребенок и жена ищем квартиру для длительного проживания. У нас четыре щеночка и барабанная установка"
        ),
        PersonED(
            "",
            null,
            "Ника Пеутина",
            19,
            null,
            listOf(),
            Metro.ACADEMIC,
            Pair(10000, 25000),
            listOf("3 комнаты"),
            "Привет, меня зовут Ника. У меня три кота. Со мной будет жить бабушка, мама, отец, два брата и маленькая сестра. Ищем квартиру для длительного проживания."
        )
    )
}

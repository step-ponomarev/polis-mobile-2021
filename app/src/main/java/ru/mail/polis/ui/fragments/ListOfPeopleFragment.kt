package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.mail.polis.R
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.list.of.people.PeopleAdapter
import ru.mail.polis.list.of.people.Person
import ru.mail.polis.metro.Metro

class ListOfPeopleFragment : Fragment(), PeopleAdapter.ListItemClickListener {
    private val personService: IPersonService = PersonService.getInstance()
    private lateinit var listOfPeople: List<Person>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_of_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvList: RecyclerView = view.findViewById(R.id.list_of_people__rv_list)

        val adapter = PeopleAdapter(emptyList(), this)
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.adapter = adapter

        GlobalScope.launch(Dispatchers.Main) {
            val people = async(Dispatchers.IO) {
                personService.findAll()
            }

            listOfPeople = toPersonView(people.await())
            adapter.setData(listOfPeople)
        }
    }
    private fun toPersonView(people: List<PersonED>): List<Person> {
        return people.filter { it.isValid() }.map {
            Person.Builder.createBuilder()
                .email(it.email!!)
                .photo(it.photo)
                .name(it.name!!)
                .age(it.age!!)
                .mark(it.mark)
                .tags(it.tags)
                .moneyFrom(it.moneyFrom)
                .moneyTo(it.moneyTo)
                .rooms(it.rooms)
                .description(it.description!!)
                .metro(it.metro!!)
                .build()
        }
    }
    override fun onListItemClick(clickedItemIndex: Int) {
        val person: Person = listOfPeople[clickedItemIndex]
        val action = ListOfPeopleFragmentDirections.actionNavGraphListOfPeopleToPersonAnnouncementFragment(person)
        findNavController().navigate(action) // R.id.nav_graf_task_fragment
    }
}

private fun generateTestPeopleList(): List<Person> {
    return listOf(
        Person(
            "",
            null,
            "Степан Пономарев",
            22,
            null,
            listOf(),
            Metro.KUPCHINO,
            20000, 35000,
            listOf("1 комната", "2 комнаты"),
            "Привет, меня зовут Степа и я не алкоголик. У меня есть ребенок и жена ищем квартиру для длительного проживания. У нас четыре щеночка и барабанная установка"
        ),
        Person(
            "",
            null,
            "Ника Пеутина",
            19,
            null,
            listOf(),
            Metro.ACADEMIC,
            10000, 25000,
            listOf("3 комнаты"),
            "Привет, меня зовут Ника. У меня три кота. Со мной будет жить бабушка, мама, отец, два брата и маленькая сестра. Ищем квартиру для длительного проживания."
        )
    )
}

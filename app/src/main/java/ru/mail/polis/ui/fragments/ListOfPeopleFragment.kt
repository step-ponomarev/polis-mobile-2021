package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.list.of.people.PeopleAdapter
import ru.mail.polis.list.of.people.Person

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

package ru.mail.polis.ui.fragments

import android.content.Context
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.R
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.list.of.people.PeopleAdapter
import ru.mail.polis.list.of.people.Person
import ru.mail.polis.viewModels.ListOfPeopleViewModel

class ListOfPeopleFragment : Fragment(), PeopleAdapter.ListItemClickListener {

    private lateinit var listOfPeople: List<Person>
    private lateinit var viewModel: ListOfPeopleViewModel

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

        val email = getEmail()
        GlobalScope.launch(Dispatchers.Main) {
            val people = withContext(Dispatchers.IO) {
                viewModel.fetchPeople()
            }

            val user = withContext(Dispatchers.IO) {
                viewModel.fetchUser(email)
            } ?: throw IllegalStateException("User with email $email not found")

            listOfPeople = toPersonView(people, user)
            adapter.setData(listOfPeople)
        }
    }

    private fun toPersonView(people: List<PersonED>, user: UserED): List<Person> {
        return people.filter { it.isValid() }.map {
            Person.Builder.createBuilder()
                .email(it.email!!)
                .photo(user.photo)
                .name("${user.name} ${user.surname}")
                .age(user.age!!)
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

    private fun getEmail(): String {
        return activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )?.getString(getString(R.string.preference_email_key), null)
            ?: throw IllegalStateException("Email not found")
    }
}

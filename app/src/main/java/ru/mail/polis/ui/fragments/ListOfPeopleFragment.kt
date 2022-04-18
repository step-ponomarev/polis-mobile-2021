package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.list.ListItemClickListener
import ru.mail.polis.list.RecyclerViewListDecoration
import ru.mail.polis.list.of.people.PeopleAdapter
import ru.mail.polis.list.of.people.PersonView
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.viewModels.ListOfPeopleViewModel
import java.util.Objects

class ListOfPeopleFragment : Fragment() {
    private lateinit var listOfPersonViews: List<PersonView>
    private lateinit var viewModel: ListOfPeopleViewModel

    private val onListItemClick = ListItemClickListener {
        val personView: PersonView = listOfPersonViews[it]
        val action =
            ListOfPeopleFragmentDirections.actionNavGraphListOfPeopleToPersonAnnouncementFragment(
                personView
            )
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_of_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListOfPeopleViewModel::class.java)

        val adapter = PeopleAdapter(emptyList(), onListItemClick)
        val rvList: RecyclerView = view.findViewById(R.id.list_of_people__rv_list)
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.addItemDecoration(RecyclerViewListDecoration())
        rvList.adapter = adapter

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val people = viewModel.fetchPeople().toMutableList()
                if (people.isEmpty()) {
                    return@launch
                }

                val emailSet = people.map { it.email!! }.toSet()
                val users = viewModel.fetchUsers(emailSet).toMutableList()

                if (users.isEmpty()) {
                    throw IllegalStateException("There are no owners of adverts $people")
                }

                if (users.size != people.size) {
                    filterData(people, users)
                }

                listOfPersonViews = toPersonView(people, users)
                adapter.setData(listOfPersonViews)
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    private fun filterData(people: MutableList<PersonED>, users: MutableList<UserED>) {
        val filterPeople: Boolean = people.size > users.size

        val emails = if (filterPeople)
            users.map { it.email!! }
        else people.map { it.email!! }

        if (filterPeople) {
            people.removeAll { !emails.contains(it.email) }
        } else {
            users.removeAll { !emails.contains(it.email) }
        }
    }

    private fun toPersonView(people: List<PersonED>, users: List<UserED>): List<PersonView> {
        return people.filter { it.isValid() }.map { person ->
            val user = users.find { Objects.equals(person.email, it.email) }!!

            PersonView.Builder.createBuilder()
                .email(person.email!!)
                .photo(user.photo)
                .name("${user.name} ${user.surname}")
                .age(user.age!!)
                .tags(person.tags)
                .moneyFrom(person.moneyFrom)
                .moneyTo(person.moneyTo)
                .metresFrom(person.metresFrom)
                .metresTo(person.metresTo)
                .rooms(person.rooms)
                .description(person.description!!)
                .metro(person.metro!!)
                .build()
        }
    }
}

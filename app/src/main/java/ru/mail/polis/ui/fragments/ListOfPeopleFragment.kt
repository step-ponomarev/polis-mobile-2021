package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mail.polis.R
import ru.mail.polis.list.of.people.PeopleAdapter
import ru.mail.polis.list.of.people.Person
import ru.mail.polis.metro.Metro

class ListOfPeopleFragment : Fragment(), PeopleAdapter.ListItemClickListener {

    private val listOfPeople = generateTestPeopleList()


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
        val adapter = PeopleAdapter(listOfPeople, this)
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.adapter = adapter
    }

    override fun onListItemClick(clickedItemIndex: Int) {
        val person: Person = listOfPeople[clickedItemIndex]
        findNavController().navigate(R.id.nav_graph__person_announcement_fragment)
    }
}

private fun generateTestPeopleList(): List<Person> {
    return listOf(
        Person(
            null,
            "Степан Пономарев",
            "22 года",
            R.mipmap.ic_mark_foreground,
            listOf(
                R.drawable.ic_ciggarete,
                R.drawable.ic_kid,
                R.drawable.ic_paw,
                R.drawable.ic_drum
            ),
            Metro.KUPCHINO,
            Pair(20000, 35000),
            listOf("1 комната", "2 комнаты"),
            "Привет, меня зовут Степа и я не алкоголик. У меня есть ребенок и жена ищем квартиру для длительного проживания. У нас четыре щеночка и барабанная установка"
        ),
        Person(
            null,
            "Ника Пеутина",
            "19 лет",
            R.mipmap.ic_mark_foreground,
            listOf(R.drawable.ic_paw),
            Metro.ACADEMIC,
            Pair(10000, 25000),
            listOf("3 комнаты"),
            "Привет, меня зовут Ника. У меня три кота. Со мной будет жить бабушка, мама, отец, два брата и маленькая сестра. Ищем квартиру для длительного проживания."
        )
    )
}

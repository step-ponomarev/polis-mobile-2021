package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import ru.mail.polis.R
import ru.mail.polis.list.of.people.Person

class PersonAnnouncementFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ivPhoto: ImageView = view.findViewById(R.id.person_header__icon)
        val tvName: TextView = view.findViewById(R.id.person_header__name)
        val tvAge: TextView = view.findViewById(R.id.person_header__age)
        val llIvTags: List<ImageView> = listOf(
            view.findViewById(R.id.fragment_person_announcement__ll_iv_tag1),
            view.findViewById(R.id.fragment_person_announcement__ll_iv_tag2),
            view.findViewById(R.id.fragment_person_announcement__ll_iv_tag3),
            view.findViewById(R.id.fragment_person_announcement__ll_iv_tag4),
            view.findViewById(R.id.fragment_person_announcement__ll_iv_tag5),
            view.findViewById(R.id.fragment_person_announcement__ll_iv_tag6),
            view.findViewById(R.id.fragment_person_announcement__ll_iv_tag7)
        )
        val ivMetro: ImageView = view.findViewById(R.id.fragment_person_announcement__iv_metro)
        val tvMetro: TextView = view.findViewById(R.id.fragment_person_announcement__tv_metro)
        val ivBranchColor: ImageView =
            view.findViewById(R.id.fragment_person_announcement__iv_branch_color)
        val ivMoney: ImageView = view.findViewById(R.id.fragment_person_announcement__iv_money)
        val tvMoney: TextView = view.findViewById(R.id.fragment_person_announcement__tv_money)
        val cvRooms: List<CardView> = listOf(
            view.findViewById(R.id.fragment_person_announcement__ll_cv_rooms1),
            view.findViewById(R.id.fragment_person_announcement__ll_cv_rooms2),
            view.findViewById(R.id.fragment_person_announcement__ll_cv_rooms3)
        )
        val tvRooms: List<TextView> = listOf(
            view.findViewById(R.id.fragment_person_announcement__ll_tv_rooms1),
            view.findViewById(R.id.fragment_person_announcement__ll_tv_rooms2),
            view.findViewById(R.id.fragment_person_announcement__ll_tv_rooms3)
        )
        val tvDescription: TextView = view.findViewById(R.id.fragment_person_announcement__tv_description)

        val person = generateTestPerson()

        super.onViewCreated(view, savedInstanceState)
        val myTextView = view.findViewById(R.id.person_header__name) as TextView
        myTextView.text = person.name

        ivPhoto.setImageResource(person.photo)
        tvName.text = person.name
        tvAge.text = person.age
        for (i in 0..6.coerceAtMost(person.tags.size - 1)) {
            llIvTags[i].setImageResource(person.tags[i])
        }
        ivMetro.setImageResource(R.drawable.ic_train)
        tvMetro.text = person.metro
        // ivBranchColor.setImageResource(person.branchColor)
        ivMoney.setImageResource(R.drawable.ic_wallet)
        tvMoney.text = "от " + person.money.first + " до " + person.money.second
        for (i in 0..3.coerceAtMost(person.rooms.size - 1)) {
            cvRooms[i].visibility = View.VISIBLE
            tvRooms[i].text = person.rooms[i]
        }
        tvDescription.text = person.description
    }
}

private fun generateTestPerson(): Person {
    return Person(
        0,
        "Степан Пономарев",
        "22 года",
        0,
        listOf(
            R.drawable.ic_ciggarete,
            R.drawable.ic_kid,
            R.drawable.ic_paw,
            R.drawable.ic_drum
        ),
        "Купчино",
        0,
        Pair(20000, 35000),
        listOf("1 комната", "2 комнаты"),
        "Привет, меня зовут Степа и я не алкоголик. У меня есть ребенок и жена ищем квартиру для длительного проживания. У нас четыре щеночка и барабанная установка"
    )
}

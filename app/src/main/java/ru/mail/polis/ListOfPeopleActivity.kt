package ru.mail.polis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListOfPeopleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_people)

        val rvList: RecyclerView = findViewById(R.id.list_of_people__rv_list)
        val adapter = PeopleAdapter(generateTestPeopleList())
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = adapter
    }
}

private fun generateTestPeopleList(): List<Person> {
    return listOf(
            Person(
                    R.drawable.photo1,
                    "Степан Пономарев",
                    "22 года",
                    listOf(),
                    "Купчино",
                    0,
                    Pair(20000, 35000),
                    listOf(),
                    "Привет, меня зовут Степа и я не алкоголик. У меня есть ребенок и жена ищем квартиру для длительного проживания. У нас четыре щеночка и барабанная установка"
            ),
            Person(
                    R.drawable.ic_account_circle_24,
                    "Ника Пеутина",
                    "19 лет",
                    listOf(),
                    "Девяткино",
                    0,
                    Pair(10000, 25000),
                    listOf(),
                    "Привет, меня зовут Ника. У меня три кота. Со мной будет жить бабушка, мама, отец, два брата и маленькая сестра. Ищем квартиру для длительного проживания."
            )
    )
}
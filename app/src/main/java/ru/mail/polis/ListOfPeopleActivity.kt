package ru.mail.polis

import android.content.Context
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
                    R.mipmap.ic_mark_foreground,
                    listOf(R.drawable.ic_ciggarete_svg_image,
                            R.drawable.ic_kid_svg_image,
                            R.drawable.ic_paw_svg_image,
                            R.drawable.ic_drum_svg_image
                    ),
                    "Купчино",
                    0,
                    Pair(20000, 35000),
                    listOf("1 комната", "2 комнаты"),
                    "Привет, меня зовут Степа и я не алкоголик. У меня есть ребенок и жена ищем квартиру для длительного проживания. У нас четыре щеночка и барабанная установка"
            ),
            Person(
                    R.drawable.ic_account_circle_24,
                    "Ника Пеутина",
                    "19 лет",
                    R.mipmap.ic_mark_foreground,
                    listOf(R.drawable.ic_paw_svg_image),
                    "Девяткино",
                    0,
                    Pair(10000, 25000),
                    listOf("3 комнаты"),
                    "Привет, меня зовут Ника. У меня три кота. Со мной будет жить бабушка, мама, отец, два брата и маленькая сестра. Ищем квартиру для длительного проживания."
            )
    )
}
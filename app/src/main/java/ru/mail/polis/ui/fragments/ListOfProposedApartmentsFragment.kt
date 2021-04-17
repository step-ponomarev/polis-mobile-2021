package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mail.polis.R
import ru.mail.polis.list.RecyclerViewListDecoration
import ru.mail.polis.list.of.apartments.Apartment
import ru.mail.polis.list.of.apartments.ApartmentsAdapter
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

class ListOfProposedApartmentsFragment : Fragment() {
    companion object {
        const val NAME = "ListOfProposedApartmentsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proposed_apartments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ApartmentsAdapter(mockAparts())
        val rvList: RecyclerView = view.findViewById(R.id.fragment_proposed_apartments__list)

        rvList.addItemDecoration(RecyclerViewListDecoration())
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.adapter = adapter
    }

    private fun mockAparts(): List<Apartment> {
        return listOf(
            Apartment(
                null,
                "Степан Пономарев",
                20,
                Metro.DEVYATKINO,
                RoomCount.TWO,
                8,
                11999,
                listOf(
                    "https://i.ytimg.com/vi/vBM8H5drE0o/maxresdefault.jpg",
                    "https://starpri.ru/wp-content/uploads/2019/02/5kenap.png",
                    "https://www.meme-arsenal.com/memes/d547850e477b4dc2a13167e12e32fb5b.jpg"
                )
            ),
            Apartment(
                null,
                "Илья Сачук",
                12,
                Metro.PARNASSUS,
                RoomCount.ONE,
                287,
                30000,
                listOf(
                    "https://i.ytimg.com/vi/vBM8H5drE0o/maxresdefault.jpg",
                    "https://starpri.ru/wp-content/uploads/2019/02/5kenap.png",
                    "https://www.meme-arsenal.com/memes/d547850e477b4dc2a13167e12e32fb5b.jpg"
                )
            ),
            Apartment(
                null,
                "Илья Сачук",
                12,
                Metro.CIVIL_PROSPECT,
                RoomCount.FOUR,
                287,
                30000,
                listOf(
                    "https://i.ytimg.com/vi/vBM8H5drE0o/maxresdefault.jpg",
                    "https://starpri.ru/wp-content/uploads/2019/02/5kenap.png",
                    "https://www.meme-arsenal.com/memes/d547850e477b4dc2a13167e12e32fb5b.jpg"
                )
            )
        )
    }
}

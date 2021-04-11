package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mail.polis.R
import ru.mail.polis.list.of.apartments.ApartmensAdaper
import ru.mail.polis.list.of.apartments.Apartment
import ru.mail.polis.list.RecyclerViewListDecoration
import ru.mail.polis.metro.Metro
import java.util.*

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

        val rvList: RecyclerView = view.findViewById(R.id.proposed_apartments__list)
        rvList.addItemDecoration(RecyclerViewListDecoration())
        val adapter = ApartmensAdaper(mockAparts())
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
                20,
                Collections.emptyList()
            ),
            Apartment(
                null,
                "Илья Сачук",
                12,
                Metro.PARNAS,
                20,
                Collections.emptyList()
            )
        )
    }
}

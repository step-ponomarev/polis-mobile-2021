package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.*
import ru.mail.polis.list.RecyclerViewListDecoration
import ru.mail.polis.list.of.apartments.ApartmentViewModel
import ru.mail.polis.list.of.apartments.ApartmentsAdapter

class ProposedApartmentsFragment : Fragment() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val proposeService: IProposeService = ProposeService.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proposed_apartments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ApartmentsAdapter()
        val rvList: RecyclerView = view.findViewById(R.id.fragment_proposed_apartments__list)

        rvList.addItemDecoration(RecyclerViewListDecoration())
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.adapter = adapter

        val email: String = "step.ponomarev@gmail.com"
        GlobalScope.launch(Dispatchers.Main) {
            val proposeList = withContext(Dispatchers.IO) {
                proposeService.findRenterEmail(email)
            }

            val apartments = async(Dispatchers.IO) {
                apartmentService.findByEmails(proposeList.map { proposeED -> proposeED.ownerEmail!! }
                    .toSet());
            }

            adapter.setData(toApartmentView(apartments.await()))
        }
    }

    private fun toApartmentView(apartments: List<ApartmentED>): List<ApartmentViewModel> {
        return apartments.filter { it.isValid() }.map {
            ApartmentViewModel.Builder.createBuilder()
                .email(it.email!!)
                .apartmentCosts(it.apartmentCosts!!)
                .apartmentSquare(it.apartmentSquare!!)
                .ownerAge(it.ownerAge!!)
                .metro(it.metro!!)
                .ownerAvatar(it.ownerAvatar)
                .ownerName(it.ownerName!!)
                .roomCount(it.roomCount!!)
                .photosUrls(it.photosUrls)
                .build()
        }
    }
}

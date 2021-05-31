package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.list.RecyclerViewListDecoration
import ru.mail.polis.list.of.apartments.ApartmentView
import ru.mail.polis.list.of.apartments.ApartmentsAdapter
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.ProposedApartmentsViewModel
import java.util.Objects

class ProposedApartmentsFragment : Fragment() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var viewModel: ProposedApartmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proposed_apartments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProposedApartmentsViewModel::class.java)

        val adapter = ApartmentsAdapter()
        val rvList: RecyclerView = view.findViewById(R.id.fragment_proposed_apartments__list)

        rvList.addItemDecoration(RecyclerViewListDecoration())
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.adapter = adapter

        val email: String = StorageUtils.getCurrentUserEmail(requireContext())
        scope.launch(Dispatchers.Main) {
            try {
                val apartments = viewModel.fetchApartmentsByRenterEmail(email).toMutableList()
                if (apartments.isEmpty()) {
                    return@launch
                }

                val emailSet = apartments.map { it.email }.toSet()
                val users = viewModel.fetchUsers(emailSet).toMutableList()

                if (users.isEmpty()) {
                    throw IllegalStateException("There are no owners of apartments $apartments")
                }

                if (users.size != apartments.size) {
                    filterData(apartments, users)
                }

                adapter.setData(toApartmentView(apartments, users))
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.coroutineContext.cancelChildren()
    }

    private fun filterData(apartments: MutableList<ApartmentED>, users: MutableList<UserED>) {
        val filterApartments: Boolean = apartments.size > users.size

        val emails = if (filterApartments)
            users.map { it.email }
        else apartments.map { it.email }

        if (filterApartments) {
            apartments.removeAll() { !emails.contains(it.email) }
        } else {
            users.removeAll { !emails.contains(it.email) }
        }
    }

    private fun toApartmentView(
        apartments: List<ApartmentED>,
        users: List<UserED>
    ): List<ApartmentView> {
        return apartments.map { apartment ->
            val user = users.find { Objects.equals(apartment.email, it.email) }!!

            ApartmentView(
                email = apartment.email,
                apartmentCosts = apartment.apartmentCosts,
                apartmentSquare = apartment.apartmentSquare,
                ownerName = "${user.name} ${user.surname}",
                ownerAge = user.age,
                ownerAvatar = user.photo,
                metro = apartment.metro,
                roomCount = apartment.roomCount,
                photosUrls = apartment.photosUrls
            )
        }
    }
}

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
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.list.ListItemClickListener
import ru.mail.polis.list.RecyclerViewListDecoration
import ru.mail.polis.list.of.apartments.ApartmentView
import ru.mail.polis.list.of.apartments.ApartmentsAdapter
import ru.mail.polis.list.of.propose.ProposeView
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.ProposedApartmentsViewModel
import java.util.Objects

class ProposedApartmentsFragment : Fragment() {
    private lateinit var viewModel: ProposedApartmentsViewModel
    private lateinit var apartments: List<ApartmentView>
    private lateinit var proposes: List<ProposeView>

    private val onListItemClick = ListItemClickListener {
        val apartment: ApartmentView = apartments[it]
        val propose: ProposeView = proposes.find { pr -> pr.ownerEmail == apartment.email }!!
        val action =
            ProposedApartmentsFragmentDirections.actionNavGraphProposedApartmentsFragmentToFragmentShowOneApartment(
                apartment,
                propose
            )
        findNavController().navigate(action)
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

        viewModel = ViewModelProvider(this).get(ProposedApartmentsViewModel::class.java)

        val adapter = ApartmentsAdapter(emptyList(), onListItemClick)
        val rvList: RecyclerView = view.findViewById(R.id.fragment_proposed_apartments__list)

        rvList.addItemDecoration(RecyclerViewListDecoration())
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.adapter = adapter

        val email: String = StorageUtils.getCurrentUserEmail(requireContext())
        GlobalScope.launch(Dispatchers.Main) {
            try {
                proposes = toProposeView(viewModel.fetchProposeByRenterEmail(email).filter { proposeED -> proposeED.status != ProposeStatus.REJECTED })
                val apartmentsMutable = viewModel.fetchApartmentsByRenterEmail(email).toMutableList()
                if (apartmentsMutable.isEmpty()) {
                    return@launch
                }

                val emailSet = apartmentsMutable.map { it.email!! }.toSet()
                val users = viewModel.fetchUsers(emailSet).toMutableList()

                if (users.isEmpty()) {
                    throw IllegalStateException("There are no owners of apartments $apartmentsMutable")
                }

                if (users.size != apartmentsMutable.size) {
                    filterData(apartmentsMutable, users)
                }

                apartments = toApartmentView(apartmentsMutable, users)
                adapter.setData(apartments)
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(
                    requireContext(),
                    getString(e.getResourceStringCode())
                )
            }
        }
    }

    private fun filterData(apartments: MutableList<ApartmentED>, users: MutableList<UserED>) {
        val filterApartments: Boolean = apartments.size > users.size

        val emails = if (filterApartments)
            users.map { it.email!! }
        else apartments.map { it.email!! }

        if (filterApartments) {
            apartments.removeAll() { !emails.contains(it.email) }
        } else {
            users.removeAll { !emails.contains(it.email) }
        }
    }

    private fun toApartmentView(
        apartmentsED: List<ApartmentED>,
        users: List<UserED>
    ): List<ApartmentView> {
        return apartmentsED.filter { it.isValid() }.map { apartment ->
            val user = users.find { Objects.equals(apartment.email, it.email) }!!

            ApartmentView.Builder.createBuilder()
                .email(apartment.email!!)
                .phone(user.phone!!)
                .apartmentCosts(apartment.apartmentCosts!!)
                .apartmentSquare(apartment.apartmentSquare!!)
                .ownerName("${user.name} ${user.surname}")
                .ownerAge(user.age!!)
                .ownerAvatar(user.photo)
                .metro(apartment.metro!!)
                .roomCount(apartment.roomCount!!)
                .photosUrls(apartment.photosUrls)
                .build()
        }
    }

    private fun toProposeView(
        proposeED: List<ProposeED>
    ): List<ProposeView> {
        return proposeED.map { propose ->
            ProposeView.Builder.createBuilder()
                .ownerEmail(propose.ownerEmail!!)
                .renterEmail(propose.renterEmail!!)
                .status(propose.status!!)
                .build()
        }
    }
}

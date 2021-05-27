package ru.mail.polis.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.list.RecyclerViewListDecoration
import ru.mail.polis.list.of.apartments.ApartmentView
import ru.mail.polis.list.of.apartments.ApartmentsAdapter
import ru.mail.polis.viewModels.ProposedApartmentsViewModel

class ProposedApartmentsFragment : Fragment() {
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

        viewModel =
            ViewModelProvider(this).get(ProposedApartmentsViewModel::class.java)

        val adapter = ApartmentsAdapter()
        val rvList: RecyclerView = view.findViewById(R.id.fragment_proposed_apartments__list)

        rvList.addItemDecoration(RecyclerViewListDecoration())
        rvList.layoutManager = LinearLayoutManager(this.context)
        rvList.adapter = adapter

        val email: String = getEmail()
        GlobalScope.launch(Dispatchers.Main) {
            val apartments = viewModel.fetchApartmentsByRenterEmail(email)

            if (apartments.isNotEmpty()) {
                val user = viewModel.fetchUser(email)
                    ?: throw IllegalStateException("No user with email $email")

                adapter.setData(toApartmentView(apartments, user))
            }
        }
    }

    private fun getEmail(): String {
        return activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )?.getString(getString(R.string.preference_email_key), null)
            ?: throw IllegalStateException("Email not found")
    }

    private fun toApartmentView(apartments: List<ApartmentED>, user: UserED): List<ApartmentView> {
        return apartments.filter { it.isValid() }.map {
            ApartmentView.Builder.createBuilder()
                .email(it.email!!)
                .apartmentCosts(it.apartmentCosts!!)
                .apartmentSquare(it.apartmentSquare!!)
                .ownerName("${user.name} ${user.surname}")
                .ownerAge(user.age!!)
                .ownerAvatar(user.photo)
                .metro(it.metro!!)
                .roomCount(it.roomCount!!)
                .photosUrls(it.photosUrls)
                .build()
        }
    }
}

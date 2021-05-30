package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mail.polis.R
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.utils.StorageUtils
import ru.mail.polis.viewModels.SelfDefinitionViewModel

class SelfDefinitionFragment : Fragment() {
    private val selfDefinitionViewModel = SelfDefinitionViewModel()
    private lateinit var addApartmentButton: Button
    private lateinit var findApartmentButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_self_definition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = StorageUtils.getCurrentUserEmail(requireContext())
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val apartments = selfDefinitionViewModel.fetchApartment(email)
                val person = selfDefinitionViewModel.fetchPerson(email)

                if (person != null || apartments != null) {
                    findNavController().navigate(R.id.nav_graph__list_of_people)
                }
            } catch (e: NotificationKeeperException) {
                NotificationCenter.showDefaultToast(requireContext(), getString(e.getResourceStringCode()))
            }
        }

        addApartmentButton =
            view.findViewById(R.id.fragment_self_definition__button_rent_apartment)
        addApartmentButton.setOnClickListener(::onClickAddApartment)

        findApartmentButton =
            view.findViewById(R.id.fragment_self_definition__button_find_apartment)
        findApartmentButton.setOnClickListener(::onClickFindApartment)
    }

    private fun onClickAddApartment(view: View) {
        findNavController().navigate(R.id.nav_graph__add_apartment_fragment)
    }

    private fun onClickFindApartment(view: View) {
        findNavController().navigate(R.id.nav_graph__advert_creation_fragment)
    }
}

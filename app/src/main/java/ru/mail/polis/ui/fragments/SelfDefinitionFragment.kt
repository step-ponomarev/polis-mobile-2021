package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.mail.polis.R

class SelfDefinitionFragment : Fragment() {

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

        addApartmentButton = view.findViewById(R.id.fragment_self_definition__button_rent_apartment)
        addApartmentButton.setOnClickListener(this::onClickAddApartment)

        findApartmentButton = view.findViewById(R.id.fragment_self_definition__button_find_apartment)
        findApartmentButton.setOnClickListener(this::onClickFindApartment)
    }

    private fun onClickAddApartment(view: View) {
        findNavController().navigate(R.id.nav_graph__add_apartment_fragment)
    }

    private fun onClickFindApartment(view: View) {
        findNavController().navigate(R.id.nav_graph__advert_creation_fragment)
    }
}

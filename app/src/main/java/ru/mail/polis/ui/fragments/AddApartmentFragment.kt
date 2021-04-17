package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.mail.polis.R
import ru.mail.polis.metro.Metro

class AddApartmentFragment : Fragment() {

    private lateinit var spinner: Spinner
    private val metroList = Metro.values()
    private lateinit var addApartmentButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_apartment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.fragment_add_apartment_spinner)

        val metroNamesList = metroList.map { it.stationName }

        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, metroNamesList)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = it
            }

        addApartmentButton = view.findViewById(R.id.add_button)
        addApartmentButton.setOnClickListener(this::onClickAddApartment)
    }

    private fun onClickAddApartment(view: View) {
        findNavController().navigate(R.id.nav_graph__list_of_people)
    }
}

package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.mail.polis.R
import ru.mail.polis.metro.Metro

class AddApartmentFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var addApartmentButton: Button
    private lateinit var metroCircleIv: ImageView
    private val metroList = Metro.values()

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
        metroCircleIv = view.findViewById(R.id.fragment_add_apartment__circle)

        val metroNamesList = metroList.map { it.stationName }

        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, metroNamesList)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = it
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val metro = Metro.values()[position]
                if (view != null) {
                    metroCircleIv.background.setTint(
                        ContextCompat.getColor(
                            view.context,
                            metro.branchColor
                        )
                    )
                }
            }

        }

        addApartmentButton = view.findViewById(R.id.add_button)
        addApartmentButton.setOnClickListener(this::onClickAddApartment)
    }

    private fun onClickAddApartment(view: View) {
        findNavController().navigate(R.id.nav_graph__list_of_people)
    }
}

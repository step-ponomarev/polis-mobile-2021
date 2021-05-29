package ru.mail.polis.ui.fragments.advert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.mail.polis.R

class AdvertEditingFragment : AdvertFragment() {

    private lateinit var editAdvertButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advert_editing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editAdvertButton = view.findViewById(R.id.fragment_advert_editing__continue_btn)

        editAdvertButton.setOnClickListener(this::onClickEditButton)
    }

    private fun onClickEditButton(view: View) {

    }

}
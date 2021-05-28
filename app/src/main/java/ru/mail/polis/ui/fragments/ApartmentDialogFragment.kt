package ru.mail.polis.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ApartmentDialogFragment(private val title: String, private val message: String) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Создать", null)
            .setNegativeButton("Отмена", null)
            .create()
    }
}
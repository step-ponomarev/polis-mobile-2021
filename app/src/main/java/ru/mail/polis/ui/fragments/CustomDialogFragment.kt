package ru.mail.polis.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CustomDialogFragment(
    private val title: String,
    private val message: String,
    private var positive: ((DialogInterface, Int) -> Unit),
    private var negative: ((DialogInterface, Int) -> Unit)
) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Создать", positive)
            .setNegativeButton("Отмена", negative)
            .create()
    }
}

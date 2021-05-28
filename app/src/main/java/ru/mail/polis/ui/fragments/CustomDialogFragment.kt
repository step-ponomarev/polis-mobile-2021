package ru.mail.polis.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CustomDialogFragment(
    private val title: String,
    private val message: String
) :
    DialogFragment() {

    private var positive: ((DialogInterface, Int) -> Unit)? = null
    private var negative: ((DialogInterface, Int) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Создать", positive)
            .setNegativeButton("Отмена", negative)
            .create()
    }

    fun setPositiveAction(positive: (DialogInterface, Int) -> Unit): CustomDialogFragment {
        this.positive = positive
        return this
    }

    fun setNegativeAction(negative: (DialogInterface, Int) -> Unit): CustomDialogFragment {
        this.negative = negative
        return this
    }
}
package ru.mail.polis.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ApartmentDialogFragment(
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

    

    class Builder private constructor() {
        private var title: String? = null
        private var message: String? = null
        private var positive: ((DialogInterface, Int) -> Unit)? = null
        private var negative: ((DialogInterface, Int) -> Unit)? = null


        companion object {
            fun createBuilder(): Builder {
                return Builder()
            }
        }

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun message(message: String): Builder {
            this.message = message
            return this
        }

        fun positiveAction(positive: (DialogInterface, Int) -> Unit): Builder {
            this.positive = positive
            return this
        }

        fun negativeAction(positive: (DialogInterface, Int) -> Unit): Builder {
            this.negative = negative
            return this
        }

        fun build(): ApartmentDialogFragment {
            return ApartmentDialogFragment(
                title ?: "",
                message ?: "",
                positive,
                negative
            )
        }
    }
}
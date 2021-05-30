package ru.mail.polis.notification

import android.content.Context
import android.widget.Toast

class NotificationCenter private constructor() {
    companion object {
        fun showDefaultToast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}

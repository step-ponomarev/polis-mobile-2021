package ru.mail.polis.helpers

import android.content.Context
import ru.mail.polis.R

fun getAgeString(age: Long, context: Context): String {
    return when {
        age % 100 in 5..20 -> {
            "$age " + context.getString(R.string.age_advert_let)
        }
        age % 10 in 2..4 -> {
            "$age " + context.getString(R.string.age_advert_goda)
        }
        age % 10 == 1L -> {
            "$age " + context.getString(R.string.age_advert_god)
        }
        else -> {
            "$age " + context.getString(R.string.age_advert_let)
        }
    }
}

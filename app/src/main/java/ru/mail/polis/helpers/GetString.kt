package ru.mail.polis.helpers

public fun getAgeString(age: Long): String {
    return when {
        age % 100 in 5..20 -> {
            "$age лет"
        }
        age % 10 in 2..4 -> {
            "$age года"
        }
        age % 10 == 1L -> {
            "$age год"
        }
        else -> {
            "$age лет"
        }
    }
}

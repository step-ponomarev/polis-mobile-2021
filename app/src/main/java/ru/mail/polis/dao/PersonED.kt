package ru.mail.polis.dao

import ru.mail.polis.metro.Metro

data class PersonED(
    val email: String,
    val photo: String?,
    val name: String,
    val age: String,
    val mark: Int,
    val tags: List<Int>,
    val metro: Metro,
    val money: Pair<Int, Int>,
    val rooms: List<String>,
    val description: String
)

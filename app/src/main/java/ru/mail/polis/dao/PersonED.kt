package ru.mail.polis.dao

import ru.mail.polis.metro.Metro

data class PersonED(
    val email: String,
    val photo: String?,
    val name: String?,
    val age: Long?,
    val mark: Long?,
    val tags: List<Long>?,
    val metro: Metro?,
    val money: Pair<Long, Long>?,
    val rooms: List<String>?,
    val description: String?
)

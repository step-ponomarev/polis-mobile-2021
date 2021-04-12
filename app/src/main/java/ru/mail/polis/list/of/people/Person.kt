package ru.mail.polis.list.of.people

import ru.mail.polis.metro.Metro

data class Person(
    val photo: Int,
    val name: String,
    val age: String,
    val mark: Int,
    val tags: List<Int>,
    val metro: Metro,
    val branchColor: Int,
    val money: Pair<Int, Int>,
    val rooms: List<String>,
    val description: String
)

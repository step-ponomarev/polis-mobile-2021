package ru.mail.polis


data class Person(
        val photo: Int,
        val name: String,
        val age: String,
        val mark: Int,
        val tags: List<Int>,
        val metro: String,
        val branchColor: Int,
        val money: Pair<Int, Int>,
        val rooms: List<String>,
        val description: String
)
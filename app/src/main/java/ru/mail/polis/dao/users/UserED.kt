package ru.mail.polis.dao.users

class UserED(
    val email: String = "",
    val name: String = "",
    val surname: String = "",
    val phone: String = "",
    val age: Long = 0,
    val photo: String? = null,
    val externalAccounts: List<String> = emptyList()
)

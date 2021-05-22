package ru.mail.polis.dao.users

class UserED(
    val email: String,
    val name: String,
    val surname: String,
    val phone: String,
    val age: Long,
    val photo: String,
    val externalAccounts: List<String>?
) {

}
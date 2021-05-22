package ru.mail.polis.dao.users

class UserED(
    var email: String,
    var name: String,
    var surname: String,
    var phone: String,
    var age: Long,
    var photo: String?,
    var externalAccounts: List<String>
)

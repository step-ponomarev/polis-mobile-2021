package ru.mail.polis.dao.users

class UserED(
    var email: String? = null,
    var name: String? = null,
    var surname: String? = null,
    var phone: String? = null,
    var age: Long? = null,
    var photo: String? = null,
    var externalAccounts: List<String> = emptyList()
)

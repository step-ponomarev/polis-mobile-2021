package ru.mail.polis.dao

import ru.mail.polis.metro.Metro

data class PersonED(
    var email: String = "",
    var photo: String? = null,
    var name: String? = null,
    var age: Long? = null,
    var mark: Long? = null,
    var tags: List<Long>? = null,
    var metro: Metro? = null,
    var money: Pair<Long, Long>? = null,
    var rooms: List<String>? = null,
    var description: String? = null
)

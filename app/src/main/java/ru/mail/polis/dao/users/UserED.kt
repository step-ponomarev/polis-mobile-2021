package ru.mail.polis.dao.users

class UserED(
    var email: String? = null,
    var name: String? = null,
    var surname: String? = null,
    var phone: String? = null,
    var age: Long? = null,
    var photo: String? = null,
    var externalAccounts: List<String> = emptyList()



) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserED

        if (email != other.email) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (phone != other.phone) return false
        if (age != other.age) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (surname?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (age?.hashCode() ?: 0)
        return result
    }
}

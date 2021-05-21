package ru.mail.polis.dao.apartments

class ProposeED(
    var ownerEmail: String? = null,
    var renterEmail: String? = null,
    var status: ProposeStatus? = null
) {
    override fun toString(): String {
        return "ProposeED(ownerEmail=$ownerEmail, renterEmail=$renterEmail, status=$status)"
    }
}

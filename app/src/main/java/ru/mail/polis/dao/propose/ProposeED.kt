package ru.mail.polis.dao.propose

class ProposeED(
    val ownerEmail: String = "",
    val renterEmail: String = "",
    val status: ProposeStatus = ProposeStatus.PENDING
) {
    override fun toString(): String {
        return "ProposeED(ownerEmail=$ownerEmail, renterEmail=$renterEmail, status=$status)"
    }
}

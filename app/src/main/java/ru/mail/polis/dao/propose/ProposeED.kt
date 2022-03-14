package ru.mail.polis.dao.propose

class ProposeED(
    var ownerEmail: String? = null,
    var renterEmail: String? = null,
    var status: ProposeStatus? = null
) {
    override fun toString(): String {
        return "ProposeED(ownerEmail=$ownerEmail, renterEmail=$renterEmail, status=$status)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProposeED

        if (ownerEmail != other.ownerEmail) return false
        if (renterEmail != other.renterEmail) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ownerEmail?.hashCode() ?: 0
        result = 31 * result + (renterEmail?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }


}

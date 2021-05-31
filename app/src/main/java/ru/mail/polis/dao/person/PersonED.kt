package ru.mail.polis.dao.person

import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount
import ru.mail.polis.tags.Tags

class PersonED(
    val email: String = "",
    val tags: List<Tags> = emptyList(),
    val metro: Metro = Metro.ACADEMIC,
    val moneyTo: Long = 0,
    val moneyFrom: Long = 0,
    val rooms: List<RoomCount> = emptyList(),
    val description: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonED

        if (email != other.email) return false
        if (tags != other.tags) return false
        if (metro != other.metro) return false
        if (moneyTo != other.moneyTo) return false
        if (moneyFrom != other.moneyFrom) return false
        if (rooms != other.rooms) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + (metro.hashCode() ?: 0)
        result = 31 * result + moneyTo.hashCode()
        result = 31 * result + moneyFrom.hashCode()
        result = 31 * result + rooms.hashCode()
        result = 31 * result + (description.hashCode() ?: 0)
        return result
    }
}

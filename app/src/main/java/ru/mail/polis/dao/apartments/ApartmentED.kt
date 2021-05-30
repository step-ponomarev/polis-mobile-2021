package ru.mail.polis.dao.apartments

import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

class ApartmentED(
    var email: String = "",
    var metro: Metro = Metro.ACADEMIC,
    var roomCount: RoomCount = RoomCount.ONE,
    var apartmentSquare: Long = 0,
    var apartmentCosts: Long = 0,
    var photosUrls: List<String> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApartmentED

        if (email != other.email) return false
        if (metro != other.metro) return false
        if (roomCount != other.roomCount) return false
        if (apartmentSquare != other.apartmentSquare) return false
        if (apartmentCosts != other.apartmentCosts) return false
        if (photosUrls != other.photosUrls) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + (metro.hashCode() ?: 0)
        result = 31 * result + (roomCount.hashCode() ?: 0)
        result = 31 * result + (apartmentSquare.hashCode() ?: 0)
        result = 31 * result + (apartmentCosts.hashCode() ?: 0)
        result = 31 * result + (photosUrls.hashCode())
        return result
    }
}

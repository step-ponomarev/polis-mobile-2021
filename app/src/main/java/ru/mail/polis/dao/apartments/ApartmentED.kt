package ru.mail.polis.dao.apartments

import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

class ApartmentED(
    var email: String? = null,
    var phone: String? = null,
    var metro: Metro? = null,
    var roomCount: RoomCount? = null,
    var apartmentSquare: Long? = null,
    var apartmentCosts: Long? = null,
    var photosUrls: List<String> = emptyList()
) {
    fun isValid(): Boolean {
        return email != null &&
            metro != null &&
            roomCount != null &&
            apartmentSquare != null &&
            apartmentCosts != null
    }

    class Builder private constructor() {
        private var email: String? = null
        private var phone: String? = null
        private var metro: Metro? = null
        private var roomCount: RoomCount? = null
        private var apartmentSquare: Long? = null
        private var apartmentCosts: Long? = null
        private var photosUrls: List<String> = emptyList()

        companion object {
            fun createBuilder(): Builder {
                return Builder()
            }
        }

        fun email(email: String): Builder {
            this.email = email
            return this
        }

        fun metro(metro: Metro): Builder {
            this.metro = metro
            return this
        }

        fun roomCount(roomCount: RoomCount): Builder {
            this.roomCount = roomCount
            return this
        }
        fun phone(phone: String): Builder {
            this.phone = phone
            return this
        }

        fun apartmentSquare(apartmentSquare: Long): Builder {
            this.apartmentSquare = apartmentSquare
            return this
        }

        fun apartmentCosts(apartmentCosts: Long): Builder {
            this.apartmentCosts = apartmentCosts
            return this
        }

        fun photosUrls(photosUrls: List<String>): Builder {
            this.photosUrls = photosUrls
            return this
        }

        fun build(): ApartmentED {
            return ApartmentED(
                email!!,
                phone,
                metro!!,
                roomCount!!,
                apartmentSquare!!,
                apartmentCosts!!,
                photosUrls
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApartmentED

        if (email != other.email) return false
        if (phone != other.phone) return false
        if (metro != other.metro) return false
        if (roomCount != other.roomCount) return false
        if (apartmentSquare != other.apartmentSquare) return false
        if (apartmentCosts != other.apartmentCosts) return false
        if (photosUrls != other.photosUrls) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (metro?.hashCode() ?: 0)
        result = 31 * result + (roomCount?.hashCode() ?: 0)
        result = 31 * result + (apartmentSquare?.hashCode() ?: 0)
        result = 31 * result + (apartmentCosts?.hashCode() ?: 0)
        result = 31 * result + (photosUrls.hashCode())
        return result
    }
}

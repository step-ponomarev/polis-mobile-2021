package ru.mail.polis.list.of.apartments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

@Parcelize
class ApartmentView(
    var email: String,
    var phone: String,
    var ownerAvatar: String?,
    var ownerName: String,
    var ownerAge: Long,
    var metro: Metro,
    var roomCount: RoomCount,
    var apartmentSquare: Long,
    var apartmentCosts: Long,
    var photosUrls: List<String> = emptyList()
) : Parcelable {
    class Builder private constructor() {
        private var email: String = ""
        private var phone: String = ""
        private var ownerAvatar: String? = null
        private var ownerName: String? = null
        private var ownerAge: Long? = null
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

        fun phone(phone: String): Builder {
            this.phone = phone
            return this
        }

        fun ownerAvatar(ownerAvatar: String?): Builder {
            this.ownerAvatar = ownerAvatar
            return this
        }

        fun ownerName(ownerName: String): Builder {
            this.ownerName = ownerName
            return this
        }

        fun ownerAge(ownerAge: Long): Builder {
            this.ownerAge = ownerAge
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

        fun build(): ApartmentView {
            return ApartmentView(
                email,
                phone,
                ownerAvatar,
                ownerName!!,
                ownerAge!!,
                metro!!,
                roomCount!!,
                apartmentSquare!!,
                apartmentCosts!!,
                photosUrls
            )
        }
    }
}

package ru.mail.polis.dao.person

import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

class PersonED(
    var email: String? = null,
    var tags: List<Long> = emptyList(),
    var metro: Metro? = null,
    var moneyTo: Long = 0,
    var moneyFrom: Long = 0,
    var rooms: List<RoomCount> = emptyList(),
    var description: String? = null
) {
    fun isValid(): Boolean {
        return email != null &&
            metro != null &&
            description != null
    }

    class Builder private constructor() {
        private var email: String? = null
        private var tags: List<Long> = emptyList()
        private var metro: Metro? = null
        private var moneyTo: Long = 0
        private var moneyFrom: Long = 0
        private var rooms: List<RoomCount> = emptyList()
        private var description: String? = null

        companion object {
            fun createBuilder(): Builder {
                return Builder()
            }
        }

        fun email(email: String): Builder {
            this.email = email
            return this
        }

        fun tags(tags: List<Long>): Builder {
            this.tags = tags
            return this
        }

        fun money(from: Long, to: Long): Builder {
            this.moneyFrom = from
            this.moneyTo = to
            return this
        }

        fun metro(metro: Metro): Builder {
            this.metro = metro
            return this
        }

        fun rooms(rooms: List<RoomCount>): Builder {
            this.rooms = rooms
            return this
        }

        fun description(description: String): Builder {
            this.description = description
            return this
        }

        fun build(): PersonED {
            return PersonED(
                email!!,
                tags,
                metro,
                moneyFrom,
                moneyTo,
                rooms,
                description
            )
        }
    }

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
        result = 31 * result + (metro?.hashCode() ?: 0)
        result = 31 * result + moneyTo.hashCode()
        result = 31 * result + moneyFrom.hashCode()
        result = 31 * result + rooms.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }
}

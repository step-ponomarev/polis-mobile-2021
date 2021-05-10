package ru.mail.polis.dao.person

import ru.mail.polis.metro.Metro

class PersonED(
    var email: String? = null,
    var photo: String? = null,
    var name: String? = null,
    var age: Int? = null,
    var mark: Long? = null,
    var tags: List<Long> = emptyList(),
    var metro: Metro? = null,
    var moneyTo: Long = 0,
    var moneyFrom: Long = 0,
    var rooms: List<String> = emptyList(),
    var description: String? = null
) {
    fun isValid(): Boolean {
        return email != null &&
            name != null &&
            age != null &&
            metro != null &&
            description != null
    }

    class Builder private constructor() {
        private var email: String? = null
        private var photo: String? = null
        private var name: String? = null
        private var age: Int? = null
        private var mark: Long? = null
        private var tags: List<Long> = emptyList()
        private var metro: Metro? = null
        private var moneyTo: Long = 0
        private var moneyFrom: Long = 0
        private var rooms: List<String> = emptyList()
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

        fun photo(photo: String): Builder {
            this.photo = photo
            return this
        }

        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun age(age: Int): Builder {
            this.age = age
            return this
        }

        fun mark(mark: Long?): Builder {
            this.mark = mark
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

        fun rooms(rooms: List<String>): Builder {
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
                photo,
                name,
                age,
                mark,
                tags!!,
                metro,
                moneyFrom,
                moneyTo,
                rooms!!,
                description
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonED

        if (email != other.email) return false
        if (photo != other.photo) return false
        if (name != other.name) return false
        if (age != other.age) return false
        if (mark != other.mark) return false
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
        result = 31 * result + (photo?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (age?.hashCode() ?: 0)
        result = 31 * result + (mark?.hashCode() ?: 0)
        result = 31 * result + (tags.hashCode())
        result = 31 * result + (metro?.hashCode() ?: 0)
        result = 31 * result + moneyTo.hashCode()
        result = 31 * result + moneyFrom.hashCode()
        result = 31 * result + (rooms.hashCode())
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }
}

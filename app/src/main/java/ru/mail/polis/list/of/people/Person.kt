package ru.mail.polis.list.of.people

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

@Parcelize
data class Person(
    var email: String? = null,
    var photo: String? = null,
    var name: String? = null,
    var age: Int? = null,
    var tags: List<Long> = emptyList(),
    var metro: Metro? = null,
    var moneyTo: Long = 0,
    var moneyFrom: Long = 0,
    var rooms: List<RoomCount> = emptyList(),
    var description: String? = null
) : Parcelable {
    class Builder private constructor() {
        private var email: String? = null
        private var photo: String? = null
        private var name: String? = null
        private var age: Int? = null
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

        fun photo(photo: String?): Builder {
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

        fun tags(tags: List<Long>): Builder {
            this.tags = tags
            return this
        }

        fun metro(metro: Metro): Builder {
            this.metro = metro
            return this
        }

        fun moneyTo(moneyTo: Long): Builder {
            this.moneyTo = moneyTo
            return this
        }

        fun moneyFrom(moneyFrom: Long): Builder {
            this.moneyFrom = moneyFrom
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

        fun build(): Person {
            return Person(
                email,
                photo,
                name,
                age,
                tags,
                metro,
                moneyTo,
                moneyFrom,
                rooms,
                description
            )
        }
    }
}

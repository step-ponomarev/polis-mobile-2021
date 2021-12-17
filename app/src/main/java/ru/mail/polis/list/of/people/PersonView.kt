package ru.mail.polis.list.of.people

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount
import ru.mail.polis.tags.Tags

@Parcelize
data class PersonView(
    var email: String? = null,
    var photo: String? = null,
    var name: String? = null,
    var age: Long? = null,
    var tags: List<Tags> = emptyList(),
    var metro: Metro? = null,
    var moneyTo: Long = 0,
    var moneyFrom: Long = 0,
    var metresTo: Long = 0,
    var metresFrom: Long = 0,
    var rooms: List<RoomCount> = emptyList(),
    var description: String? = null
) : Parcelable {
    class Builder private constructor() {
        private var email: String? = null
        private var photo: String? = null
        private var name: String? = null
        private var age: Long? = null
        private var tags: List<Tags> = emptyList()
        private var metro: Metro? = null
        private var moneyTo: Long = 0
        private var moneyFrom: Long = 0
        private var metresTo: Long = 0
        private var metresFrom: Long = 0
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

        fun age(age: Long): Builder {
            this.age = age
            return this
        }

        fun tags(tags: List<Tags>): Builder {
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

        fun metresTo(metresTo: Long): Builder {
            this.metresTo = metresTo
            return this
        }

        fun metresFrom(metresFrom: Long): Builder {
            this.metresFrom = metresFrom
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

        fun build(): PersonView {
            return PersonView(
                email,
                photo,
                name,
                age,
                tags,
                metro,
                moneyTo,
                moneyFrom,
                metresTo,
                metresFrom,
                rooms,
                description
            )
        }
    }
}

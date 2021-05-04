package ru.mail.polis.dao.person

import ru.mail.polis.metro.Metro
import java.util.Objects

class PersonED(
    var email: String = "",
    var photo: String? = null,
    var name: String? = null,
    var age: Long? = null,
    var mark: Long? = null,
    var tags: List<Long>? = null,
    var metro: Metro? = null,
    var money: Pair<Long, Long>? = null,
    var rooms: List<String>? = null,
    var description: String? = null
) {
    class Builder private constructor() {
        private var email: String = ""
        private var photo: String? = null
        private var name: String? = null
        private var age: Long? = null
        private var mark: Long? = null
        private var tags: List<Long>? = null
        private var metro: Metro? = null
        private var money: Pair<Long, Long>? = null
        private var rooms: List<String>? = null
        private var description: String? = null

        companion object {
            fun createBuilder(): Builder {
                return Builder()
            }
        }

        fun setEmail(email: String): Builder {
            this.email = email
            return this
        }

        fun setPhoto(photo: String?): Builder {
            this.photo = photo
            return this
        }

        fun setName(name: String?): Builder {
            this.name = name
            return this
        }

        fun setAge(age: Long?): Builder {
            this.age = age
            return this
        }

        fun setMark(mark: Long?): Builder {
            this.mark = mark
            return this
        }

        fun setTags(tags: List<Long>?): Builder {
            this.tags = tags
            return this
        }

        fun setMoney(money: Pair<Long, Long>?): Builder {
            this.money = money
            return this
        }

        fun setMetro(metro: Metro?): Builder {
            this.metro = metro
            return this
        }

        fun setRooms(rooms: List<String>?): Builder {
            this.rooms = rooms
            return this
        }

        fun setDescription(description: String?): Builder {
            this.description = description
            return this
        }

        fun build(): PersonED {
            return PersonED(email, photo, name, age, mark, tags, metro, money, rooms, description)
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
        if (money != other.money) return false
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
        result = 31 * result + (tags?.hashCode() ?: 0)
        result = 31 * result + (metro?.hashCode() ?: 0)
        result = 31 * result + (money?.hashCode() ?: 0)
        result = 31 * result + (rooms?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }


}

package ru.mail.polis.room

enum class RoomCount(val roomCount: Int, val label: String) {
    STUDIO(1, "Студия"),
    ONE(1, "1 конмата"),
    TWO(2, "2 комнаты"),
    THREE(2, "3 комнаты"),
    FOUR(2, "4 комнаты"),
    FIVE_MORE(2, "5+ комнат");

    companion object {
        fun fromString(roomCount: String): RoomCount {
            values().forEach {
                if (it.label == roomCount) {
                    return it
                }
            }
            throw IllegalArgumentException("No enum constant with name $roomCount")
        }
    }
}

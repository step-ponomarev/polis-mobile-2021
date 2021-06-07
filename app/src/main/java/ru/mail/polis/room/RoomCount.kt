package ru.mail.polis.room

import ru.mail.polis.R

enum class RoomCount(val roomCount: Int, val label: String, val stringId: Int) {
    STUDIO(1, "Студия", R.string.studio_text),
    ONE(1, "1 комната", R.string.one_room_text),
    TWO(2, "2 комнаты", R.string.two_room_text),
    THREE(2, "3 комнаты", R.string.three_room_text),
    FOUR(2, "4 комнаты", R.string.four_room_text),
    FIVE_MORE(2, "5+ комнат", R.string.five_plus_room_text);

    companion object {
        fun from(roomCount: String): RoomCount {
            values().forEach {
                if (it.label == roomCount) {
                    return it
                }
            }
            throw IllegalArgumentException("No enum constant with name $roomCount")
        }
    }
}

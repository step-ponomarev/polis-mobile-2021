package ru.mail.polis.room

enum class RoomCount(val roomCount: Int, val label: String) {
    ONE(1, "1 конмата"),
    TWO(2, "2 комнаты"),
    THREE(2, "3 комнаты"),
    FOUR(2, "4 комнаты"),
    FIVE_MORE(2, "5+ комнат"),
}

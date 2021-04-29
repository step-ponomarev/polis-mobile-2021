package ru.mail.polis.dao

import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

data class ApartmentED(
    var email: String = "",
    var ownerAvatar: String? = null,
    var ownerName: String? = null,
    var ownerAge: Long? = null,
    var metro: Metro? = null,
    var roomCount: RoomCount? = null,
    var apartmentSquare: Long? = null,
    var apartmentCosts: Long? = null,
    var photosUrls: List<String>? = null
)

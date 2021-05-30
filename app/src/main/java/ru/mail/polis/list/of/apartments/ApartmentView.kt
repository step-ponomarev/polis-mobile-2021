package ru.mail.polis.list.of.apartments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

@Parcelize
class ApartmentView(
    val email: String,
    val ownerAvatar: String?,
    val ownerName: String,
    val ownerAge: Long,
    val metro: Metro,
    val roomCount: RoomCount,
    val apartmentSquare: Long,
    val apartmentCosts: Long,
    val photosUrls: List<String> = emptyList()
) : Parcelable

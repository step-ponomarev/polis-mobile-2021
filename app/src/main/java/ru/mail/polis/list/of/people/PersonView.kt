package ru.mail.polis.list.of.people

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount
import ru.mail.polis.tags.Tags

@Parcelize
data class PersonView(
    val email: String = "",
    val photo: String? = null,
    val name: String = "",
    val age: Long = 0,
    val tags: List<Tags> = emptyList(),
    val metro: Metro = Metro.ACADEMIC,
    val moneyTo: Long = 0,
    val moneyFrom: Long = 0,
    val rooms: List<RoomCount> = emptyList(),
    val description: String = ""
) : Parcelable

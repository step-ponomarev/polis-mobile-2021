package ru.mail.polis.list.of.people

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mail.polis.metro.Metro

@Parcelize
data class Person(
    val photo: String?,
    val name: String,
    val age: String,
    val mark: Int,
    val tags: List<Int>,
    val metro: Metro,
    val money: Pair<Int, Int>,
    val rooms: List<String>,
    val description: String
) : Parcelable

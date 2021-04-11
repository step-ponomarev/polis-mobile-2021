package ru.mail.polis.list.of.apartments

import ru.mail.polis.metro.Metro

data class Apartment(
    val ownerAvatar: Int?,
    val ownerName: String,
    val ownerAge: Int,
    val metro: Metro,
    val apartmentSquare: Int,
    val apartmentCosts: Int,
    val apartmentPhotos: List<Int>
)

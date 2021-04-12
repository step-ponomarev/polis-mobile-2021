package ru.mail.polis.list.of.apartments

import ru.mail.polis.metro.Metro

data class Apartment(
    val ownerAvatar: String?,
    val ownerName: String,
    val ownerAge: Int,
    val metro: Metro,
    val apartmentSquare: Int,
    val apartmentCosts: Int,
    val photosUrls: List<String>
)

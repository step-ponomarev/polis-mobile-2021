package ru.mail.polis.list.of.apartments

import ru.mail.polis.metro.Metro

data class Apartment(
    val photo: Int,
    val ownerName: String,
    val ownerAge: String,
    val metro: Metro,
    val apartmentSquare: Int,
    val apartmentPhotos: List<String>
)
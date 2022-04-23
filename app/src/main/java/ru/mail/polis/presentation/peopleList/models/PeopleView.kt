package ru.mail.polis.presentation.peopleList.models

data class PeopleView(
    var id: Long? = 0,
    var peopleName: String? = "",
    var voteAverage: Double? = 0.0,
    var profilePath: String? = "",
    var isBookMarked: Boolean
)

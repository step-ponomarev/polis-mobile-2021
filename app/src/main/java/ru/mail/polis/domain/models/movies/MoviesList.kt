package ru.mail.polis.domain.models.movies

data class MoviesList(
    var page: Int = 0,
    var results: MutableList<People> = mutableListOf(),
    var totalPages: Int = 0,
    var totalResults: Int = 0
)

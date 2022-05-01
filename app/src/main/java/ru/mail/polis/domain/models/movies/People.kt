package ru.mail.polis.domain.models.movies

data class People(
    var adult: Boolean = false,
    var backdropPath: String? = "",
    var name: String? = "",
    var genreIds: List<Int> = listOf(),
    var id: Long? = 0,
    var originalLanguage: String = "",
    var originalTitle: String? = "",
    var overview: String = "",
    var popularity: Double = 0.0,
    var posterPath: String? = "",
    var releaseDate: String? = "",
    var firstAirDate: String? = "",
    var title: String? = "",
    var knownForDepartment: String = "",
    var profilePath: String? = "",
    var video: Boolean = false,
    var voteAverage: Double? = 0.0,
    var voteCount: Int = 0,
    var isAdded: Boolean

)

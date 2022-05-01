package ru.mail.polis.domain.models.movies

import ru.mail.polis.domain.models.movies.Cast
import ru.mail.polis.domain.models.movies.Crew

data class MovieCredits(
    var cast: List<Cast> = listOf(),
    var crew: List<Crew> = listOf(),
    var id: Int = 0
)

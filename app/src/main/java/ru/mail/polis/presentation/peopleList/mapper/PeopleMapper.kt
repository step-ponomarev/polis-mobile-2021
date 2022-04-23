package ru.mail.polis.presentation.peopleList.mapper

import ru.mail.polis.domain.models.movies.People
import ru.mail.polis.presentation.mapper.Mapper
import ru.mail.polis.presentation.peopleList.models.PeopleView
import javax.inject.Inject

class PeopleMapper @Inject constructor() : Mapper<PeopleView, People> {

    override fun mapToView(type: People): PeopleView {
        return PeopleView(
            id = type.id,
            profilePath = PROFILE_URL_PREFIX.plus(type.posterPath),
            peopleName = type.title ?: type.originalTitle,
            isBookMarked = type.isAdded,
            voteAverage = type.voteAverage
        )
    }

    companion object {
        const val PROFILE_URL_PREFIX = "https://image.tmdb.org/t/p/w185"
    }
}

package ru.mail.polis.integr.presentation.factory.people

import ru.mail.polis.domain.models.movies.People
import ru.mail.polis.integr.presentation.factory.DataFactory
import ru.mail.polis.presentation.peopleList.mapper.PeopleMapper
import ru.mail.polis.presentation.peopleList.models.PeopleView

object PresentationPeopleFactory {

    fun generateListOfMovieViews(size: Int): MutableList<PeopleView> {
        val listOfMoviesViews = mutableListOf<PeopleView>()
        repeat(size) {
            listOfMoviesViews.add(generateMovieView())
        }
        return listOfMoviesViews
    }

    fun generateListOfPeople(size: Int): MutableList<People> {
        val listOfMovies = mutableListOf<People>()
        repeat(size) {
            listOfMovies.add(generateMovie())
        }
        return listOfMovies
    }

    fun generateMovieView(): PeopleView {
        return PeopleView(
            id = DataFactory.getRandomLong(),
            voteAverage = DataFactory.getRandomDouble(),
            peopleName = DataFactory.getRandomString(),
            isBookMarked = DataFactory.getRandomBoolean(),
            profilePath = PeopleMapper.PROFILE_URL_PREFIX.plus(DataFactory.getRandomString())
        )
    }

    fun generateMovie(): People {
        return People(
            adult = DataFactory.getRandomBoolean(),
            isAdded = DataFactory.getRandomBoolean(),
            id = DataFactory.getRandomLong(),
            name = DataFactory.getRandomString(),
            title = DataFactory.getRandomString(),
            voteAverage = DataFactory.getRandomDouble(),
            profilePath = DataFactory.getRandomString(),
            posterPath = DataFactory.getRandomString(),
            popularity = DataFactory.getRandomDouble(),
            backdropPath = DataFactory.getRandomString(),
            firstAirDate = DataFactory.getRandomString(),
            genreIds = listOf(DataFactory.getRandomInt(), DataFactory.getRandomInt()),
            knownForDepartment = DataFactory.getRandomString(),
            originalLanguage = DataFactory.getRandomString(),
            originalTitle = DataFactory.getRandomString(),
            overview = DataFactory.getRandomString(),
            releaseDate = DataFactory.getRandomString(),
            video = DataFactory.getRandomBoolean(),
            voteCount = DataFactory.getRandomInt()
        )
    }
}

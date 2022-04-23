package ru.mail.polis.integr.presentation.peopleList.mapper

import ru.mail.polis.domain.models.movies.People
import ru.mail.polis.integr.presentation.factory.people.PresentationPeopleFactory
import ru.mail.polis.presentation.peopleList.models.PeopleView
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import ru.mail.polis.presentation.peopleList.mapper.PeopleMapper

@RunWith(MockitoJUnitRunner::class)
class PeopleMapperTest {

    private lateinit var peopleMapper: PeopleMapper

    @Before
    fun setUp() {
        peopleMapper = PeopleMapper()
    }

    @Test
    fun mapToView() {
        // Arrange
        val movie = PresentationPeopleFactory.generateMovie()

        // Act
        val movieView = peopleMapper.mapToView(movie)

        // Assert
        assertMovieMapDataEqual(movieView, movie)
    }

    /**
     * Helpers Methods
     */
    private fun assertMovieMapDataEqual(peopleView: PeopleView, people: People) {
        assertEquals(peopleView.profilePath, PeopleMapper.PROFILE_URL_PREFIX.plus(people.posterPath))
        assertEquals(peopleView.id, people.id)
        assertEquals(peopleView.isBookMarked, people.isAdded)
        assertEquals(peopleView.voteAverage, people.voteAverage)
        assertEquals(peopleView.peopleName, people.title ?: people.originalTitle)
    }
}

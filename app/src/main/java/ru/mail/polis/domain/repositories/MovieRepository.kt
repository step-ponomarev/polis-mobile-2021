package ru.mail.polis.domain.repositories

import ru.mail.polis.domain.models.movies.People
import ru.mail.polis.domain.models.movies.MovieCredits
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {
    fun getPopularMovies(): Single<List<People>>
    fun getMovieCredits(movieId: Long): Single<MovieCredits>
    fun saveMovies(listPeople: List<People>): Completable
    fun bookmarkMovie(movieId: Long): Completable
    fun unBookmarkMovie(movieId: Long): Completable
    fun getBookMarkedMovies(): Single<List<People>>
}

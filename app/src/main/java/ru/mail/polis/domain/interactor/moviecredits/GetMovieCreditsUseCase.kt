package ru.mail.polis.domain.interactor.moviecredits

import ru.mail.polis.domain.SingleUseCase
import ru.mail.polis.domain.executor.PostExecutionThread
import ru.mail.polis.domain.executor.ThreadExecutor
import ru.mail.polis.domain.models.movies.MovieCredits
import ru.mail.polis.domain.repositories.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<GetMovieCreditsUseCase.Params, MovieCredits>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(requestValues: Params?): Single<MovieCredits> {
        return movieRepository.getMovieCredits(requestValues!!.movieId.toLong())
    }

    /**
     * [movieId] to fetch MovieCredits
     */
    data class Params(
        val movieId: Int
    )
}

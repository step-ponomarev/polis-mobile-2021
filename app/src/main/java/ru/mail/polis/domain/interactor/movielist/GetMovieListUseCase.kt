package ru.mail.polis.domain.interactor.movielist

import ru.mail.polis.domain.SingleUseCase
import ru.mail.polis.domain.executor.PostExecutionThread
import ru.mail.polis.domain.executor.ThreadExecutor
import ru.mail.polis.domain.models.movies.People
import ru.mail.polis.domain.repositories.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Void, List<People>>(
    threadExecutor, postExecutionThread
) {
    public override fun buildUseCaseObservable(requestValues: Void?): Single<List<People>> {
        return movieRepository.getPopularMovies()
    }
}

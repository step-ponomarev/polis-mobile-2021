package ru.mail.polis.domain.interactor.movielist

import ru.mail.polis.domain.CompletableUseCase
import ru.mail.polis.domain.executor.PostExecutionThread
import ru.mail.polis.domain.executor.ThreadExecutor
import ru.mail.polis.domain.repositories.MovieRepository
import io.reactivex.Completable
import javax.inject.Inject

class UnBookmarkMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Long>(
    threadExecutor, postExecutionThread
) {
    public override fun buildUseCaseObservable(requestValues: Long?): Completable {
        return movieRepository.unBookmarkMovie(requestValues!!)
    }
}

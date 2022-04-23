package ru.mail.polis.presentation.peopleList.viewmodel

import androidx.lifecycle.MutableLiveData
import ru.mail.polis.domain.interactor.movielist.BookmarkMovieUseCase
import ru.mail.polis.domain.interactor.movielist.GetMovieListUseCase
import ru.mail.polis.domain.interactor.movielist.UnBookmarkMovieUseCase
import ru.mail.polis.domain.models.movies.People
import ru.mail.polis.presentation.base.BaseViewModel
import ru.mail.polis.presentation.peopleList.mapper.PeopleMapper
import ru.mail.polis.presentation.peopleList.models.PeopleView
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver

class PeopleListViewModel constructor(
    private val peopleMapper: PeopleMapper,
    private val getMovieListUseCase: GetMovieListUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unBookmarkMovieUseCase: UnBookmarkMovieUseCase
) : BaseViewModel<PeopleState>() {

    private var state: PeopleState = PeopleState.Init
        set(value) {
            field = value
            publishState(value)
        }

    fun fetchPeopleList() {
        state = PeopleState.Loading
        val singleObserver = object : DisposableSingleObserver<List<People>>() {
            override fun onSuccess(t: List<People>) {
                state = PeopleState.PeopleListSuccess(t.map { peopleMapper.mapToView(it) })
            }

            override fun onError(e: Throwable) {
                state = PeopleState.Error(e.localizedMessage)
            }
        }
        getMovieListUseCase.execute(singleObserver)
    }

    fun onOfferStatusChanged(peopleView: PeopleView) {
        val singleObserver = object : DisposableCompletableObserver() {

            override fun onComplete() {
                state = PeopleState.OfferChangeSuccess
            }

            override fun onError(e: Throwable) {
                state = PeopleState.Error(e.localizedMessage)
            }
        }
        if (peopleView.isBookMarked)
            bookmarkMovieUseCase.execute(singleObserver, peopleView.id)
        else
            unBookmarkMovieUseCase.execute(singleObserver, peopleView.id)
    }

    fun fetchMostOfferedPeopleList() {
        state = PeopleState.Loading
        val singleObserver = object : DisposableSingleObserver<List<People>>() {
            override fun onSuccess(t: List<People>) {
                state = PeopleState.PeopleListSuccess(t.map { peopleMapper.mapToView(it) })
            }

            override fun onError(e: Throwable) {
                state = PeopleState.Error(e.localizedMessage)
            }
        }
        getMovieListUseCase.execute(singleObserver)
    }

    fun fetchPeopleYouOfferedPeopleList() {
        state = PeopleState.Loading
        val singleObserver = object : DisposableSingleObserver<List<People>>() {
            override fun onSuccess(t: List<People>) {
                state = PeopleState.PeopleListSuccess(t.map { peopleMapper.mapToView(it) })
            }

            override fun onError(e: Throwable) {
                state = PeopleState.Error(e.localizedMessage)
            }
        }
        getMovieListUseCase.execute(singleObserver)
    }

    override val stateObservable: MutableLiveData<PeopleState> by lazy {
        MutableLiveData<PeopleState>()
    }
}

package ru.mail.polis.integr.presentation.peopleList.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ru.mail.polis.domain.executor.PostExecutionThread
import ru.mail.polis.domain.executor.ThreadExecutor
import ru.mail.polis.domain.interactor.movielist.BookmarkMovieUseCase
import ru.mail.polis.domain.interactor.movielist.GetMovieListUseCase
import ru.mail.polis.domain.interactor.movielist.UnBookmarkMovieUseCase
import ru.mail.polis.domain.models.movies.People
import ru.mail.polis.domain.repositories.MovieRepository
import ru.mail.polis.integr.presentation.factory.people.PresentationPeopleFactory
import ru.mail.polis.presentation.peopleList.mapper.PeopleMapper
import ru.mail.polis.presentation.peopleList.models.PeopleView
import ru.mail.polis.integr.presentation.schedulers.TestingPostExecutionThread
import ru.mail.polis.integr.presentation.schedulers.TestingThreadExecutor
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.mail.polis.presentation.peopleList.viewmodel.PeopleListViewModel
import ru.mail.polis.presentation.peopleList.viewmodel.PeopleState

@RunWith(MockitoJUnitRunner::class)
class PeopleListViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var peopleRepository: MovieRepository

    @Mock
    private lateinit var stateObserver: Observer<PeopleState>

    private var peopleMapper = PeopleMapper()

    private lateinit var getPeopleListUseCase: GetMovieListUseCase

    private lateinit var offerPeopleUseCase: BookmarkMovieUseCase

    private lateinit var deleteOfferPeopleUseCase: UnBookmarkMovieUseCase

    private lateinit var peopleListViewModel: PeopleListViewModel

    private lateinit var threadExecutor: ThreadExecutor

    private lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setUpThreadForRxJava()
        setUpUseCases()
        setUpViewModel()
    }

    private fun setUpThreadForRxJava() {
        threadExecutor = TestingThreadExecutor()
        postExecutionThread = TestingPostExecutionThread()
    }

    private fun setUpUseCases() {
        getPeopleListUseCase =
            GetMovieListUseCase(peopleRepository, threadExecutor, postExecutionThread)
        offerPeopleUseCase =
            BookmarkMovieUseCase(peopleRepository, threadExecutor, postExecutionThread)
        deleteOfferPeopleUseCase =
            UnBookmarkMovieUseCase(peopleRepository, threadExecutor, postExecutionThread)
    }

    private fun setUpViewModel() {
        peopleListViewModel = PeopleListViewModel(
            peopleMapper,
            getPeopleListUseCase,
            offerPeopleUseCase,
            deleteOfferPeopleUseCase
        )
        peopleListViewModel.stateObservable.observeForever(stateObserver)
    }

    @Test
    fun fetchPeopleListAndReturnEmpty() {
        // Arrange
        stubGetPeople(Single.just(listOf()))

        // Act
        peopleListViewModel.fetchPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.PeopleListSuccess(listOf()))
    }

    @Test
    fun fetchPeopleListAndReturnError() {
        // Arrange
        stubGetPeople(Single.error(TestingException(TestingException.GENERIC_EXCEPTION_MESSAGE)))

        // Act
        peopleListViewModel.fetchPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.Error(TestingException.GENERIC_EXCEPTION_MESSAGE))
    }

    @Test
    fun fetchPeopleListAndReturnData() {
        // Arrange
        val listOfPeople = PresentationPeopleFactory.generateListOfPeople(10)
        val listOfViews = mutableListOf<PeopleView>()
        listOfPeople.forEach {
            listOfViews.add(peopleMapper.mapToView(it))
        }
        stubGetPeople(Single.just(listOfPeople))

        // Act
        peopleListViewModel.fetchPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.PeopleListSuccess(listOfViews))
    }

    @Test
    fun offerPeopleTest() {
        // Arrange
        val listOfPeople = PresentationPeopleFactory.generateListOfPeople(10)

        val peopleToOffer = listOfPeople[0]
        peopleToOffer.isAdded = true
        val movieView = peopleMapper.mapToView(peopleToOffer)

        stubGetPeople(Single.just(listOfPeople))
        stubOfferPeople(peopleToOffer.id!!, Completable.error(TestingException()))

        // Act
        peopleListViewModel.onOfferStatusChanged(movieView)

        // Assert
        verify(stateObserver).onChanged(PeopleState.Error(TestingException.GENERIC_EXCEPTION_MESSAGE))
    }

    @Test
    fun offerPeopleComplete() {
        // Arrange
        val listOfPeople = PresentationPeopleFactory.generateListOfPeople(10)

        val peopleToOffer = listOfPeople[0]
        peopleToOffer.isAdded = true
        val movieView = peopleMapper.mapToView(peopleToOffer)

        stubGetPeople(Single.just(listOfPeople))
        stubOfferPeople(peopleToOffer.id!!, Completable.complete())

        // Act
        peopleListViewModel.onOfferStatusChanged(movieView)

        // Assert
        verify(stateObserver).onChanged(PeopleState.OfferChangeSuccess)
    }

    @Test
    fun delelteOfferPeopleError() {
        // Arrange
        val listOfPeople = PresentationPeopleFactory.generateListOfPeople(10)

        val peopleToOffer = listOfPeople[0] //
        peopleToOffer.isAdded = false
        val movieView = peopleMapper.mapToView(peopleToOffer)

        stubGetPeople(Single.just(listOfPeople))
        stubUnOfferPeople(peopleToOffer.id!!, Completable.error(TestingException()))

        // Act
        peopleListViewModel.onOfferStatusChanged(movieView)

        // Assert
        verify(stateObserver).onChanged(PeopleState.Error(TestingException.GENERIC_EXCEPTION_MESSAGE))
    }

    @Test
    fun deleteOfferPeopleComplete() {
        // Arrange
        val listOfPeople = PresentationPeopleFactory.generateListOfPeople(10)

        val peopleToOffer = listOfPeople[0]
        peopleToOffer.isAdded = false
        val movieView = peopleMapper.mapToView(peopleToOffer)

        stubGetPeople(Single.just(listOfPeople))
        stubUnOfferPeople(peopleToOffer.id!!, Completable.complete())

        // Act
        peopleListViewModel.onOfferStatusChanged(movieView)

        // Assert
        verify(stateObserver).onChanged(PeopleState.OfferChangeSuccess)
    }

    @Test
    fun fetchMostOfferedPeopleListAndReturnEmpty() {
        // Arrange
        stubGetPeople(Single.just(listOf()))

        // Act
        peopleListViewModel.fetchMostOfferedPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.PeopleListSuccess(listOf()))
    }

    @Test
    fun fetchMostOfferedPeopleListAndReturnError() {
        // Arrange
        stubGetPeople(Single.error(TestingException(TestingException.GENERIC_EXCEPTION_MESSAGE)))

        // Act
        peopleListViewModel.fetchMostOfferedPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.Error(TestingException.GENERIC_EXCEPTION_MESSAGE))
    }

    @Test
    fun fetchMostOfferedPeopleListAndReturnData() {
        // Arrange
        val listOfPeople = PresentationPeopleFactory.generateListOfPeople(10)
        val listOfViews = mutableListOf<PeopleView>()
        listOfPeople.forEach {
            listOfViews.add(peopleMapper.mapToView(it))
        }
        stubGetPeople(Single.just(listOfPeople))

        // Act
        peopleListViewModel.fetchMostOfferedPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.PeopleListSuccess(listOfViews))
    }

    @Test
    fun fetchPeopleYouOfferedListAndReturnEmpty() {
        // Arrange
        stubGetPeople(Single.just(listOf()))

        // Act
        peopleListViewModel.fetchPeopleYouOfferedPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.PeopleListSuccess(listOf()))
    }

    @Test
    fun fetchPeopleYouOfferedListAndReturnError() {
        // Arrange
        stubGetPeople(Single.error(TestingException(TestingException.GENERIC_EXCEPTION_MESSAGE)))

        // Act
        peopleListViewModel.fetchPeopleYouOfferedPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.Error(TestingException.GENERIC_EXCEPTION_MESSAGE))
    }

    @Test
    fun fetchNPeopleYouOfferedAndReturnData() {
        // Arrange
        val listOfPeople = PresentationPeopleFactory.generateListOfPeople(10)
        val listOfViews = mutableListOf<PeopleView>()
        listOfPeople.forEach {
            listOfViews.add(peopleMapper.mapToView(it))
        }
        stubGetPeople(Single.just(listOfPeople))

        // Act
        peopleListViewModel.fetchPeopleYouOfferedPeopleList()

        // Assert
        verify(stateObserver).onChanged(PeopleState.Loading)
        verify(stateObserver).onChanged(PeopleState.PeopleListSuccess(listOfViews))
    }

    /**
     * Stub Helpers Methods
     */
    private fun stubGetPeople(single: Single<List<People>>) {
        `when`(getPeopleListUseCase.buildUseCaseObservable())
            .thenReturn(single)
    }

    private fun stubOfferPeople(movieId: Long, completable: Completable) {
        `when`(offerPeopleUseCase.buildUseCaseObservable(movieId))
            .thenReturn(completable)
    }

    private fun stubUnOfferPeople(movieId: Long, completable: Completable) {
        `when`(deleteOfferPeopleUseCase.buildUseCaseObservable(movieId))
            .thenReturn(completable)
    }

    class TestingException(message: String = GENERIC_EXCEPTION_MESSAGE) : Exception(message) {
        companion object {
            const val GENERIC_EXCEPTION_MESSAGE = "Something error came while executing"
        }
    }
}

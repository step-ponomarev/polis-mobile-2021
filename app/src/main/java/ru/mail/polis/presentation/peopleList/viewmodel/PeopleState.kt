package ru.mail.polis.presentation.peopleList.viewmodel

import ru.mail.polis.presentation.peopleList.models.PeopleView

sealed class PeopleState {
    object Init : PeopleState()
    object Loading : PeopleState()
    data class Error(var message: String) : PeopleState()
    data class PeopleListSuccess(var listOfPeopleViews: List<PeopleView>) : PeopleState()
    object OfferChangeSuccess : PeopleState()
}

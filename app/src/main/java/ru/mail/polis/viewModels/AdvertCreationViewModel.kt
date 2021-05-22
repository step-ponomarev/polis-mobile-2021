package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService

class AdvertCreationViewModel : ViewModel() {
    private val personService: IPersonService = PersonService.getInstance()

    suspend fun addPerson(personED: PersonED) {
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            personService.addPerson(personED)
        }
    }
}
package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService

class SelfDefinitionViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val personService: IPersonService = PersonService.getInstance()

    suspend fun fetchApartment(email: String): ApartmentED? {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            apartmentService.findByEmail(email)
        }
    }

    suspend fun fetchPerson(email: String): PersonED? {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            personService.findByEmail(email)
        }
    }
}

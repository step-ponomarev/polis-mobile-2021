package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class AdvertViewModel : ViewModel() {
    private val personService: IPersonService = PersonService.getInstance()
    private val userService: IUserService = UserService()

    suspend fun addPerson(personED: PersonED) {
        withContext(Dispatchers.IO) {
            personService.addPerson(personED)
        }
    }

    suspend fun updatePerson(person: PersonED): PersonED {
        return withContext(Dispatchers.IO) {
            personService.updatePerson(person)
        }
    }

    suspend fun getPersonByEmail(email: String): PersonED? {
        return withContext(Dispatchers.IO) {
            personService.findByEmail(email)
        }
    }

    suspend fun fetchUser(email: String): UserED? {
        return withContext(Dispatchers.IO) {
            userService.findUserByEmail(email)
        }
    }
}

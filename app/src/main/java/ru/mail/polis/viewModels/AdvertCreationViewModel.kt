package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.exception.DaoException
import ru.mail.polis.exception.NotificationKeeperException

class AdvertCreationViewModel : ViewModel() {
    private val personService: IPersonService = PersonService.getInstance()
    private val userService: IUserService = UserService()

    @Throws(NotificationKeeperException::class)
    suspend fun addPerson(personED: PersonED): PersonED {
        try {
            return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                personService.addPerson(personED)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed adding person: $personED",
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun fetchUser(email: String): UserED? {
        try {
            return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                userService.findUserByEmail(email)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed fetching user by email: $email",
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }
}

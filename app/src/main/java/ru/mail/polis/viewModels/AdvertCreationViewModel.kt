package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.DaoResult
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationKeeperException

class AdvertCreationViewModel : ViewModel() {
    private var userED = MutableStateFlow<DaoResult<UserED?>>(DaoResult.Success(UserED()))
    val user: StateFlow<DaoResult<UserED?>> = userED

    private val personService: IPersonService = PersonService.getInstance()
    private val userService: IUserService = UserService()

    @Throws(NotificationKeeperException::class)
    suspend fun addPerson(personED: PersonED): PersonED {
        try {
            return withContext(Dispatchers.IO) {
                personService.addPerson(personED)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed adding person: $personED",
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    fun fetchUser(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userED.value = userService.findUserByEmail(email)
        }
    }
}

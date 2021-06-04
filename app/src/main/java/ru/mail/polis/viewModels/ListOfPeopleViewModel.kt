package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationKeeperException

class ListOfPeopleViewModel : ViewModel() {
    private val personService: IPersonService = PersonService.getInstance()
    private val userService: IUserService = UserService()

    @Throws(NotificationKeeperException::class)
    suspend fun fetchPeople(): List<PersonED> {
        try {
            return withContext(Dispatchers.IO) {
                personService.findAll()
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException("Failed fetching people", e, NotificationKeeperException.NotificationType.DAO_ERROR)
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun fetchUsers(emailList: Set<String>): List<UserED> {
        try {
            return withContext(Dispatchers.IO) {
                userService.findUsersByEmails(emailList)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed fetching users by email list: $emailList",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }
}

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

class ListOfPeopleViewModel : ViewModel() {
    private val personService: IPersonService = PersonService.getInstance()
    private val userService: IUserService = UserService()

    suspend fun fetchPeople(): List<PersonED> {
        return withContext(Dispatchers.IO) {
            personService.findAll()
        }
    }

    suspend fun fetchUsers(emailList: Set<String>): List<UserED> {
        return withContext(Dispatchers.IO) {
            userService.findUsersByEmails(emailList)
        }
    }
}

package ui.utils

import kotlinx.coroutines.runBlocking
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class ServiceUtils {
    companion object {
        private val userService: IUserService = UserService()
        private val apartmentService: IApartmentService = ApartmentService.getInstance()
        private val personService: IPersonService = PersonService.getInstance()
        //    private val userService: IUserService = UserService()

        fun createUser(userED: UserED) {
            runBlocking {
                userService.addUser(userED)
            }
        }

        fun deleteUser(userED: UserED) {
            runBlocking {
                userService.deleteUser(userED.email!!)
            }
        }

        fun deleteApartment(apartmentED: ApartmentED) {
            runBlocking {
                apartmentService.deleteApartmentByEmail(apartmentED.email!!)
            }
        }

        fun deletePerson(personED: PersonED) {
            runBlocking {
                personService.deletePersonByEmail(personED.email!!)
            }
        }
    }
}
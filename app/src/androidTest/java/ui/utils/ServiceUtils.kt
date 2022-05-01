package ui.utils

import kotlinx.coroutines.runBlocking
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class ServiceUtils {
    companion object {
        private val userService: IUserService = UserService()
        private val apartmentService: IApartmentService = ApartmentService.getInstance()
        private val personService: IPersonService = PersonService.getInstance()
        private val proposeService: IProposeService = ProposeService()
        //    private val userService: IUserService = UserService()

        fun createUser(userED: UserED) {
            runBlocking {
                userService.addUser(userED)
            }
        }

        fun createApartment(apartmentED: ApartmentED) {
            runBlocking {
                apartmentService.addApartment(apartmentED)
            }
        }

        fun createAdvert(personED: PersonED) {
            runBlocking {
                personService.addPerson(personED)
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

        fun deletePropose(owner: UserED, renter: UserED) {
            val propose = ProposeED(
                ownerEmail = owner.email,
                renterEmail = renter.email,
                ProposeStatus.PENDING
            )
            runBlocking {
                proposeService.deletePropose(propose)
            }
        }
    }
}
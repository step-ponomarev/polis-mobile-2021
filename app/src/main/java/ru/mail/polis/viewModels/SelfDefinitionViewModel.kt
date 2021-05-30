package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.notification.NotificationKeeperException

class SelfDefinitionViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val personService: IPersonService = PersonService.getInstance()

    @Throws(NotificationKeeperException::class)
    suspend fun fetchApartment(email: String): ApartmentED? {
        try {
            return withContext(Dispatchers.IO) {
                apartmentService.findByEmail(email)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed fetching apartment by email $email",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun fetchPerson(email: String): PersonED? {
        try {
            return withContext(Dispatchers.IO) {
                personService.findByEmail(email)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed fetching person with email $email",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }
}

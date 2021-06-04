package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.notification.NotificationKeeperException
import kotlin.coroutines.resumeWithException

class PersonAnnouncementViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val proposeService: IProposeService = ProposeService()

    @Throws(NotificationKeeperException::class)
    suspend fun offerApartment(ownerEmail: String, renterEmail: String) {
        val exist = withContext(Dispatchers.IO) {
            proposeService.checkProposeExist(ownerEmail, renterEmail)
        }

        if (exist) {
            return
        }

        withContext(Dispatchers.IO) {
            apartmentService.findByEmail(ownerEmail)
        } ?: return suspendCancellableCoroutine { coroutine ->
            coroutine.resumeWithException(
                NotificationKeeperException(
                    "Apartment is not exist email: $ownerEmail",
                    null,
                    NotificationKeeperException.NotificationType.DAO_ERROR
                )
            )
        }

        val propose = ProposeED(
            ownerEmail,
            renterEmail,
            ProposeStatus.PENDING
        )

        try {
            withContext(Dispatchers.IO) {
                proposeService.createPropose(propose)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed propose creation: $propose",
                null,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }
}

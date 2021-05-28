package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.exception.NotificationException

class PersonAnnouncementViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val proposeService: IProposeService = ProposeService()

    @Throws(NotificationException::class)
    suspend fun offerApartment(ownerEmail: String, renterEmail: String) {
        val exist = withContext(Dispatchers.IO) {
            proposeService.checkProposeExist(ownerEmail, renterEmail)
        }

        if (exist) {
            return suspendCancellableCoroutine { coroutine ->
                coroutine.cancel(
                    NotificationException(
                        "Apartments proposed already",
                        null,
                        "Вы уже предложили квартиру этому человеку"
                    )
                )
            }
        }

        withContext(Dispatchers.IO) {
            apartmentService.findByEmail(ownerEmail)
        } ?: return suspendCancellableCoroutine { coroutine ->
            coroutine.cancel(
                NotificationException(
                    "Apartment is not exist",
                    null,
                    "У вас нет доступных квартир"
                )
            )
        }

        withContext(Dispatchers.IO) {
            proposeService.createPropose(
                ProposeED(
                    ownerEmail,
                    renterEmail,
                    ProposeStatus.PENDING
                )
            )
        }
    }
}

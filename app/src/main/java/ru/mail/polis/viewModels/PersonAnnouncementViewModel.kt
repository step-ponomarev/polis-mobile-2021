package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.exception.NotificationKeeperException

class PersonAnnouncementViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val proposeService: IProposeService = ProposeService()

    @Throws(NotificationKeeperException::class)
    suspend fun offerApartment(ownerEmail: String, renterEmail: String) {
        val exist = withContext(Dispatchers.IO) {
            proposeService.checkProposeExist(ownerEmail, renterEmail)
        }
        if (exist) {
            return suspendCancellableCoroutine { coroutine ->
                coroutine.cancel(
                    NotificationKeeperException(
                        "Apartments proposed already",
                        null,
                        R.string.toast_apartment_already_proposed
                    )
                )
            }
        }

        withContext(Dispatchers.IO) {
            apartmentService.findByEmail(ownerEmail)
        } ?: return suspendCancellableCoroutine { coroutine ->

            coroutine.cancel(
                NotificationKeeperException(
                    "Apartment is not exist",
                    null,
                    R.string.toast_no_apartments_yet
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

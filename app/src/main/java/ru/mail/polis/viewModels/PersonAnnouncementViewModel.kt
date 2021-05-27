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

class PersonAnnouncementViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val proposeService: IProposeService = ProposeService()

    @Throws(IllegalStateException::class)
    suspend fun offerApartment(ownerEmail: String, renterEmail: String) {
        // Проверяем, что у пользователя
        // есть аппартаменты иначе выбрасывам исключение
        val apartments = withContext(Dispatchers.IO) {
            apartmentService.findByEmail(ownerEmail)
        }

        withContext(Dispatchers.IO) {
            if (apartments == null) {
                return@withContext
            }

            proposeService.createPropose(
                ProposeED(
                    ownerEmail,
                    renterEmail,
                    ProposeStatus.PENDING
                )
            )
        }

        return suspendCancellableCoroutine { coroutine ->
            if (apartments == null) {
                coroutine.cancel(IllegalStateException("No apartments"))
            }
        }
    }
}

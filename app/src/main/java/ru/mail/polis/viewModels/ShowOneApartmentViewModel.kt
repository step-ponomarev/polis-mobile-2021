package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.notification.NotificationKeeperException

class ShowOneApartmentViewModel : ViewModel() {
    private val proposeService: IProposeService = ProposeService.getInstance()

    @Throws(NotificationKeeperException::class)
    suspend fun updateProposeED(proposeED: ProposeED): ProposeED {
        try {
            return withContext(Dispatchers.IO) {
                proposeService.updatePropose(proposeED)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed to update propose $proposeED",
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }
}

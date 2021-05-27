package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    fun offerApartment(ownerEmail: String, renterEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val exist = withContext(Dispatchers.IO) {
                proposeService.checkProposeExist(ownerEmail, renterEmail)
            }

            if (exist) {
                return@launch
            }

            // Проверяем, что у пользователя
            // есть аппартаменты иначе выбрасывам исключение
            withContext(Dispatchers.IO) {
                apartmentService.findByEmail(ownerEmail)
            } ?: throw IllegalStateException("You have not got an apartment yet!")

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
}

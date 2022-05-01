package ru.mail.polis.viewModels

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.notification.NotificationKeeperException

class PersonAnnouncementViewModelTest {

    private val personAnnouncementViewModel = PersonAnnouncementViewModel()
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val proposeService: IProposeService = ProposeService()

    @Test
    fun notSuccessfulWhenOwnerHasNotApartment() {
        runBlocking {
            val ownerEmail = TestData.testOwnerEmail
            val renterEmail = TestData.testRenterEmail

            try {
                personAnnouncementViewModel.offerApartment(ownerEmail, renterEmail)
            } catch (e: Exception) {
                assertTrue(e is NotificationKeeperException)
            }

            val checkProposeExist = proposeService.checkProposeExist(ownerEmail, renterEmail)

            assertFalse(checkProposeExist)
        }
    }

    @Test

    fun successfulOffer() {
        runBlocking {
            val apartment = TestData.createTestApartment()


            val ownerEmail = apartment.email!!
            val renterEmail = TestData.testRenterEmail

            apartmentService.addApartment(apartment)

            personAnnouncementViewModel.offerApartment(ownerEmail, renterEmail)

            val checkProposeExist = proposeService.checkProposeExist(ownerEmail, renterEmail)

            assertTrue(checkProposeExist)

            val propose = ProposeED(
                ownerEmail,
                renterEmail,
                ProposeStatus.PENDING
            )

            deletePropose(propose)
            deleteApartment(apartment.email!!)
        }
    }

    private suspend fun deleteApartment(email: String) {
        apartmentService.deleteApartmentByEmail(email)
    }


    private suspend fun deletePropose(proposeED: ProposeED) {
        proposeService.deletePropose(proposeED)
    }

}
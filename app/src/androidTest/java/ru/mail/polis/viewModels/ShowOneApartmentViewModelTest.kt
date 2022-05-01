package ru.mail.polis.viewModels

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.notification.NotificationKeeperException

class ShowOneApartmentViewModelTest {

    private val showOneApartmentViewModel = ShowOneApartmentViewModel()
    private val proposeService: IProposeService = ProposeService.getInstance()
    private val testOwnerEmail = "testOwnerPropose@mail.ru"
    private val testRenterEmail = "testRenterPropose@mail.ru"


    @Test
    fun checkUpdatingPropose() {
        println("kek")

        runBlocking {
            println("kek")

            val propose = getPropose()
            val updatedPropose = getUpdatedPropose()
            println("kek")

            proposeService.createPropose(propose)
            println("kek")

            showOneApartmentViewModel.updateProposeED(updatedPropose)
            println("kek")

            val createPropose = proposeService.checkProposeExist(
                updatedPropose.ownerEmail!!,
                updatedPropose.renterEmail!!
            )
            println("kek")

            assertTrue(createPropose)

            proposeService.deletePropose(updatedPropose)
        }
    }


    //TODO
    @Test
    fun checkNotExistProposeToUpdate() {
        runBlocking {
            val propose = getPropose()

            try {
                showOneApartmentViewModel.updateProposeED(propose)
            } catch (e: Exception) {
                assertTrue(e is NotificationKeeperException)
            }

            val createPropose =
                proposeService.checkProposeExist(propose.ownerEmail!!, propose.renterEmail!!)

            assertFalse(createPropose)
        }
    }

    private fun getPropose(): ProposeED {
        return ProposeED(
            ownerEmail = testOwnerEmail,
            renterEmail = testRenterEmail,
            status = ProposeStatus.PENDING
        )
    }

    private fun getUpdatedPropose(): ProposeED {
        val updated = getPropose()
        updated.status = ProposeStatus.ACCEPTED
        return updated
    }
}
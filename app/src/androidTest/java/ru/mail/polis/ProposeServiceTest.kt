package ru.mail.polis

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.propose.ProposeStatus
import java.lang.IllegalStateException

class ProposeServiceTest {
    private val proposeService: IProposeService = ProposeService()
    private val testOwnerEmail = "testOwnerPropose@mail.ru"
    private val testRenterEmail = "testRenterPropose@mail.ru"

    @Test
    fun createProposeTest() {
        runBlocking {
            val propose = getPropose()

            proposeService.createPropose(propose)

            var isExist = proposeService.checkProposeExist(testOwnerEmail, testRenterEmail)

            Assert.assertTrue(isExist)

            proposeService.deletePropose(propose)

            isExist = proposeService.checkProposeExist(testOwnerEmail, testRenterEmail)

            Assert.assertFalse(isExist)
        }
    }

    @Test
    fun updateProposeTest() {
        runBlocking {
            val propose = getPropose()
            val updatedPropose = getUpdatedPropose()

            proposeService.createPropose(propose)

            proposeService.updatePropose(updatedPropose)

            val isUpdatedExist = proposeService.checkProposeExist(
                updatedPropose.ownerEmail ?: throw IllegalStateException(),
                updatedPropose.renterEmail ?: throw IllegalStateException()
            )

            Assert.assertTrue(isUpdatedExist)

            val proposedFromDb =
                proposeService.findOwnerEmail(updatedPropose.ownerEmail ?: throw IllegalStateException())

            Assert.assertTrue(proposedFromDb[0].status == updatedPropose.status)

            proposeService.deletePropose(updatedPropose)
            val isExist = proposeService.checkProposeExist(
                updatedPropose.ownerEmail ?: throw IllegalStateException(),
                updatedPropose.renterEmail ?: throw IllegalStateException()
            )
            Assert.assertFalse(isExist)
        }
    }

    @Test
    fun deleteProposeTest() {
        runBlocking {
            val propose = getPropose()

            proposeService.createPropose(propose)

            var isExist = proposeService.checkProposeExist(
                propose.ownerEmail ?: throw IllegalStateException(),
                propose.renterEmail ?: throw IllegalStateException()
            )

            Assert.assertTrue(isExist)

            proposeService.deletePropose(propose)

            isExist = proposeService.checkProposeExist(
                propose.ownerEmail ?: throw IllegalStateException(),
                propose.renterEmail ?: throw IllegalStateException()
            )

            Assert.assertFalse(isExist)
        }
    }

    @Test
    fun findRenterEmail() {
        runBlocking {
            val propose = getPropose()
            val oneRenterList = getTenProposesWithOneRenter()


            oneRenterList.forEach {
                proposeService.createPropose(it)
            }


            val proposeList =
                proposeService.findRenterEmail(propose.renterEmail ?: throw IllegalStateException())


            var isCorrectList = true;
            proposeList.forEach {
                if (!oneRenterList.contains(it)) {
                    isCorrectList = false;
                }
            }


            Assert.assertTrue(isCorrectList)

            oneRenterList.forEach {
                proposeService.deletePropose(it)
            }
        }
    }

    @Test
    fun findOwnerEmail() {
        runBlocking {
            val propose = getPropose()
            val oneRenterList = getTenProposesWithOneOwner()


            oneRenterList.forEach {
                proposeService.createPropose(it)
            }


            val proposeList =
                proposeService.findOwnerEmail(propose.ownerEmail ?: throw IllegalStateException())


            var isCorrectList = true;
            proposeList.forEach {
                if (!oneRenterList.contains(it)) {
                    isCorrectList = false;
                }
            }


            Assert.assertTrue(isCorrectList)

            oneRenterList.forEach {
                proposeService.deletePropose(it)
            }
        }
    }

    @Test
    fun checkProposeExistTest() {
        runBlocking {
            val propose = getPropose()

            proposeService.createPropose(propose)

            var isExist = proposeService.checkProposeExist(testOwnerEmail, testRenterEmail)

            Assert.assertTrue(isExist)

            proposeService.deletePropose(propose)

            isExist = proposeService.checkProposeExist(testOwnerEmail, testRenterEmail)

            Assert.assertFalse(isExist)
        }
    }

    private fun getTenProposesWithOneOwner(): List<ProposeED> {
        val list = ArrayList<ProposeED>()

        for (i in 1..10) {
            list.add(
                ProposeED(
                    ownerEmail = testOwnerEmail,
                    renterEmail = testRenterEmail + 1,
                    status = ProposeStatus.PENDING
                )
            )
        }

        return list
    }

    private fun getTenProposesWithOneRenter(): List<ProposeED> {
        val list = ArrayList<ProposeED>()

        for (i in 1..10) {
            list.add(
                ProposeED(
                    ownerEmail = testOwnerEmail + i,
                    renterEmail = testRenterEmail,
                    status = ProposeStatus.PENDING
                )
            )
        }

        return list
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
package ru.mail.polis.unit

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeService
import java.lang.IllegalStateException

class ProposeServiceTest {
    private val proposeService: IProposeService = ProposeService()
    private val testOwnerEmail = "testOwnerPropose@mail.ru"
    private val testRenterEmail = "testRenterPropose@mail.ru"

    @Test
    fun createProposeTest() {
        runBlocking {
            val propose = TestData.getPropose()

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
            val propose = TestData.getPropose()
            val updatedPropose = TestData.getUpdatedPropose()

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
            val propose = TestData.getPropose()

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
            val propose = TestData.getPropose()
            val oneRenterList = TestData.getTenProposesWithOneRenter()


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
            val propose = TestData.getPropose()
            val oneRenterList = TestData.getTenProposesWithOneOwner()


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
            val propose = TestData.getPropose()

            proposeService.createPropose(propose)

            var isExist = proposeService.checkProposeExist(testOwnerEmail, testRenterEmail)

            Assert.assertTrue(isExist)

            proposeService.deletePropose(propose)

            isExist = proposeService.checkProposeExist(testOwnerEmail, testRenterEmail)

            Assert.assertFalse(isExist)
        }
    }
}
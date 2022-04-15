package ru.mail.polis.viewModels

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.metro.Metro
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.room.RoomCount

class AdvertViewModelTest {

    private val advertViewModel = AdvertViewModel()
    private val personService: IPersonService = PersonService.getInstance()
    private val userService: IUserService = UserService()


    @Test
    fun checkThatAddedTest() {
        runBlocking {
            val personED = TestData.createTestPerson()

            advertViewModel.addPerson(personED)

            val personFromDb = advertViewModel.getPersonByEmail(personED.email!!)

            assertTrue(personFromDb?.equals(personED) ?: false)

            deletePerson(personED.email!!)
        }
    }

    @Test
    fun checkNotExistingPerson() {
        runBlocking {
            val personED = TestData.createTestPerson()

            val fetchUser = advertViewModel.fetchUser(personED.email!!)

            assertFalse(fetchUser?.equals(personED) ?: false)
        }
    }

    @Test
    fun deletePersonTest() {
        runBlocking {
            val personED = TestData.createTestPerson()

            advertViewModel.addPerson(personED)

            var personFromDb = advertViewModel.getPersonByEmail(personED.email!!)

            assertTrue(personFromDb?.equals(personED) ?: false)

            deletePerson(personED.email!!)

            personFromDb = advertViewModel.getPersonByEmail(personED.email!!)

            assertTrue(personFromDb == null)
        }
    }

    @Test
    fun deleteNotExistingPerson() {
        runBlocking {
            val personED = TestData.createTestPerson()

            try {
                deletePerson(personED.email!!)
            } catch (e: Exception) {
                assertTrue(e is DaoException)
            }

            val personFromDb = advertViewModel.getPersonByEmail(personED.email!!)

            assertTrue(personFromDb == null)
        }
    }

    @Test
    fun updatePerson() {
        runBlocking {
            val personED = TestData.createTestPerson()
            val updatedPersonED = TestData.createUpdatedPerson(personED)

            advertViewModel.addPerson(personED)

            var personFromDb = advertViewModel.getPersonByEmail(personED.email!!)

            assertTrue(personFromDb?.equals(personED) ?: false)

            advertViewModel.updatePerson(updatedPersonED)

            personFromDb = advertViewModel.getPersonByEmail(updatedPersonED.email!!)

            assertTrue(personFromDb?.equals(updatedPersonED) ?: false)


            deletePerson(updatedPersonED.email!!)
        }
    }

    @Test
    fun updateNotExistingPerson() {
        runBlocking {
            val personED = TestData.createTestPerson()

            try {
                advertViewModel.updatePerson(personED)
            } catch (e: Exception) {
                assertTrue(e is NotificationKeeperException)
            }

            val personFromDb = advertViewModel.getPersonByEmail(personED.email!!)

            assertTrue(personFromDb == null)
        }
    }

    @Test
    fun checkUserExist() {
        runBlocking {
            val userED = TestData.getUser()

            userService.addUser(userED)

            var fetchUser = advertViewModel.fetchUser(userED.email!!)

            assertTrue(fetchUser?.equals(userED) ?: false)

            userService.deleteUser(userED.email!!)

            fetchUser = advertViewModel.fetchUser(userED.email!!)

            assertTrue(fetchUser == null)
        }
    }

    private suspend fun deletePerson(email: String) {
        personService.deletePersonByEmail(email)
    }
}
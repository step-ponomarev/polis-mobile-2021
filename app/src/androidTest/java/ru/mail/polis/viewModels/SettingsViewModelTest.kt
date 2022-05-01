package ru.mail.polis.viewModels

import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationKeeperException

class SettingsViewModelTest {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val personService: IPersonService = PersonService.getInstance()
    private val userService: IUserService = UserService()
    private val photoUriService: IPhotoUriService = PhotoUriService()
    private val settingsViewModel = SettingsViewModel()


    @Test
    fun successfullyUpdate() {
        runBlocking {
            val user = TestData.getUser()
            val updatedUser = TestData.getUpdatedUser()

            userService.addUser(user)

            var findUserByEmail = userService.findUserByEmail(user.email!!)

            assertTrue(findUserByEmail?.equals(user) ?: false)

            settingsViewModel.updateUser(updatedUser, TestData.getBitMap(InstrumentationRegistry.getInstrumentation().targetContext))

            findUserByEmail = userService.findUserByEmail(updatedUser.email!!)

            assertTrue(findUserByEmail?.equals(updatedUser) ?: false)

            deleteUser(updatedUser)
        }
    }

    @Test
    fun updateNotExistingUser() {
        runBlocking {
            val user = TestData.getUser()

            try {
                settingsViewModel.updateUser(user, TestData.getBitMap(InstrumentationRegistry.getInstrumentation().targetContext))
            } catch (e: Exception) {
                assertTrue(e is NotificationKeeperException)
            }

            val findUserByEmail = userService.findUserByEmail(user.email!!)

            assertTrue(findUserByEmail == null)
        }
    }

    @Test
    fun checkExistingApartment() {
        runBlocking {
            val apartment = TestData.createTestApartment()
            apartmentService.addApartment(apartment)

            val checkApartmentExist = settingsViewModel.checkApartmentExist(apartment.email!!)

            assertTrue(checkApartmentExist)

            deleteApartment(apartment)
        }
    }

    @Test
    fun checkNotExistingApartment() {
        runBlocking {
            val apartment = TestData.createTestApartment()

            val checkApartmentExist = settingsViewModel.checkApartmentExist(apartment.email!!)

            assertFalse(checkApartmentExist)
        }
    }

    @Test
    fun checkExistingAdvert() {
        runBlocking {
            val person = TestData.createTestPerson()

            personService.addPerson(person)

            val checkAdvertExist = settingsViewModel.checkAdvertExist(person.email!!)

            assertTrue(checkAdvertExist)

            deletePerson(person)
        }
    }

    @Test
    fun checkNotExistingAdvert() {
        runBlocking {
            val person = TestData.createTestPerson()

            val checkAdvertExist = settingsViewModel.checkAdvertExist(person.email!!)

            assertFalse(checkAdvertExist)
        }
    }

    @Test
    fun successFetchUser() {
        runBlocking {
            val user = TestData.getUser()

            userService.addUser(user)

            settingsViewModel.getUserInfo(user.email!!)

            val userLiveData = settingsViewModel.getUser()
            assertTrue(userLiveData.value?.equals(user) ?: false)


            deleteUser(user)
        }
    }

    @Test
    fun notSuccessFetchUser() {
        runBlocking {
            val user = TestData.getUser()
            try {
                settingsViewModel.getUserInfo(user.email!!)
            } catch (e: Exception) {
                assertTrue(e is IllegalStateException)
            }

            val exist = userService.isExist(user.email!!)
            assertFalse(exist)
        }
    }

    private suspend fun deleteUser(user: UserED) {
        userService.deleteUser(user.email!!)
    }

    private suspend fun deleteApartment(apartment: ApartmentED) {
        apartmentService.deleteApartmentByEmail(apartment.email!!)
    }

    private suspend fun deletePerson(personED: PersonED) {
        personService.deletePersonByEmail(personED.email!!)
    }
}
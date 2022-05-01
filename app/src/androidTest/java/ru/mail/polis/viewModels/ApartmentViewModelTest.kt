package ru.mail.polis.viewModels

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.metro.Metro
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.room.RoomCount

class ApartmentViewModelTest {

    private val userService: IUserService = UserService()
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val photoUriService: IPhotoUriService = PhotoUriService()

    private val apartmentViewModel = ApartmentViewModel()

    @Test
    fun addApartmentAndCheckThatExist() {
        runBlocking {
            val apartment = TestData.createTestApartment()

            apartmentViewModel.addApartment(apartment)

            val apartmentFromDb = apartmentViewModel.getApartmentByEmail(apartment.email!!)

            assertTrue(apartment.equals(apartmentFromDb))

            deleteApartment(apartment.email!!)
        }
    }

    @Test
    fun updateApartmentTest() {
        runBlocking {
            val apartment = TestData.createTestApartment()
            val updatedApartment = TestData.createUpdatedApartment(apartment)

            apartmentViewModel.addApartment(apartment)

            apartmentViewModel.updateApartment(updatedApartment)

            val fromDb = apartmentViewModel.getApartmentByEmail(updatedApartment.email!!)

            assertTrue(fromDb?.equals(updatedApartment) ?: false)

            deleteApartment(updatedApartment.email!!)
        }
    }

    @Test
    fun updateNotExistingApartment() {
        runBlocking {
            val apartment = TestData.createTestApartment()

            try {
                apartmentViewModel.updateApartment(apartment)
            } catch (e: Exception) {
                assertTrue(e is NotificationKeeperException)
            }

            val fromDb = apartmentViewModel.getApartmentByEmail(apartment.email!!)

            assertTrue(fromDb == null)
        }
    }

    @Test
    fun checkNotExistingApartment() {
        runBlocking {
            val apartment = TestData.createTestApartment()

            val apartmentFromDb = apartmentViewModel.getApartmentByEmail(apartment.email!!)

            assertTrue(apartmentFromDb == null)
        }
    }

    @Test
    fun checkUserExist() {
        runBlocking {
            val userED = TestData.getUser()

            userService.addUser(userED)

            var fetchUser = apartmentViewModel.fetchUser(userED.email!!)

            assertTrue(fetchUser?.equals(userED) ?: false)

            userService.deleteUser(userED.email!!)

            fetchUser = apartmentViewModel.fetchUser(userED.email!!)

            assertTrue(fetchUser == null)
        }
    }

    private suspend fun deleteApartment(email: String) {
        apartmentService.deleteApartmentByEmail(email)
    }
}
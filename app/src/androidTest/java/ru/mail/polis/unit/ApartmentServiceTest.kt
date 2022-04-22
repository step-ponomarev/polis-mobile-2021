package ru.mail.polis.unit

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService

class ApartmentServiceTest {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()

    @Test
    fun apartmentShouldBeAddedSuccessful() {
        runBlocking {
            val apartment = TestData.createTestApartment()

            val addedPerson = apartmentService.addApartment(apartment)
            val gotPerson = apartmentService.findByEmail(addedPerson.email!!)

            Assert.assertNotNull(gotPerson)
            Assert.assertEquals(addedPerson, gotPerson)

            apartmentService.deleteApartmentByEmail(addedPerson.email!!)
        }
    }

    @Test
    fun deletingShouldBeSuccessful() {
        runBlocking {
            val apartment = TestData.createTestApartment()

            val addedPerson = apartmentService.addApartment(apartment)
            apartmentService.deleteApartmentByEmail(addedPerson.email!!)

            val gotPerson = apartmentService.findByEmail(addedPerson.email!!)

            Assert.assertNull(gotPerson)
        }
    }

    @Test
    fun updatingShouldBeSuccessful() {
        runBlocking {
            val apartment = TestData.createTestApartment()
            val updatedApartment = TestData.createUpdatedApartment(apartment)

            val addedApartment = apartmentService.addApartment(apartment)
            apartmentService.updateApartment(updatedApartment)
            val gotApartment = apartmentService.findByEmail(apartment.email!!)

            Assert.assertEquals(gotApartment, updatedApartment)

            apartmentService.deleteApartmentByEmail(addedApartment.email!!)
        }
    }
}

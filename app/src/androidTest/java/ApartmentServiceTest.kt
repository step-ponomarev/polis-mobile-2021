import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.dao.ApartmentED
import ru.mail.polis.dao.ApartmentService
import ru.mail.polis.dao.IApartmentService

class ApartmentServiceTest {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()

    @Test
    fun apartmentShouldBeAddedSuccessful() {
        runBlocking {
            val apartment = createTestApartment()

            val addedPerson = apartmentService.addApartment(apartment)
            val gotPerson = apartmentService.findByEmail(addedPerson.email)

            Assert.assertNotNull(gotPerson)
            Assert.assertEquals(addedPerson, gotPerson)

            apartmentService.deleteApartmentByEmail(addedPerson.email)
        }
    }

    @Test
    fun deletingShouldBeSuccessful() {
        runBlocking {
            val apartment = createTestApartment()

            val addedPerson = apartmentService.addApartment(apartment)
            apartmentService.deleteApartmentByEmail(addedPerson.email)

            val gotPerson = apartmentService.findByEmail(addedPerson.email)

            Assert.assertNull(gotPerson)
        }
    }

    @Test
    fun updatingShouldBeSuccessful() {
        runBlocking {
            val apartment = createTestApartment()
            val updatedApartment = createUpdatedApartment(apartment)

            val addedApartment = apartmentService.addApartment(apartment)
            apartmentService.updateApartment(updatedApartment)
            val gotApartment = apartmentService.findByEmail(apartment.email)

            Assert.assertEquals(gotApartment, updatedApartment)

            apartmentService.deleteApartmentByEmail(addedApartment.email)
        }
    }

    private fun createTestApartment(): ApartmentED {
        return ApartmentED(
            "test@test.test", "test", "test", 0,
            null, null, null, null, null
        )
    }

    private fun createUpdatedApartment(oldApartment: ApartmentED): ApartmentED {
        return ApartmentED(
            oldApartment.email, "${oldApartment.ownerAvatar}1", "${oldApartment.ownerName}1",
            null, null, null,
            null, null, null
        )
    }
}

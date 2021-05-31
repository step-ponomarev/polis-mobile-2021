import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

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
        return ApartmentED.Builder.createBuilder()
            .email("test@test.test")
            .metro(Metro.DEVYATKINO)
            .roomCount(RoomCount.FIVE_MORE)
            .apartmentSquare(200L)
            .apartmentCosts(300)
            .photosUrls(listOf("test"))
            .build()
    }

    private fun createUpdatedApartment(oldApartment: ApartmentED): ApartmentED {
        return ApartmentED.Builder.createBuilder()
            .email(oldApartment.email)
            .metro(oldApartment.metro)
            .roomCount(oldApartment.roomCount)
            .apartmentSquare(oldApartment.apartmentSquare)
            .apartmentCosts(oldApartment.apartmentCosts)
            .photosUrls(oldApartment.photosUrls)
            .build()
    }
}

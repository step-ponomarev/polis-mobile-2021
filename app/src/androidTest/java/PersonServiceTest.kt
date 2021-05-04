import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService

class PersonServiceTest {
    private val personService: IPersonService = PersonService.getInstance()

    @Test
    fun personShouldBeAddedSuccessful() {
        runBlocking {
            val person = createTestPerson()

            val addedPerson = personService.addPerson(person)
            val gotPerson = personService.findByEmail(addedPerson.email)

            Assert.assertNotNull(gotPerson)
            Assert.assertEquals(addedPerson, gotPerson)

            personService.deletePersonByEmail(addedPerson.email)
        }
    }

    @Test
    fun deletingShouldBeSuccessful() {
        runBlocking {
            val person = createTestPerson()

            val addedPerson = personService.addPerson(person)
            personService.deletePersonByEmail(addedPerson.email)

            val gotPerson = personService.findByEmail(addedPerson.email)

            Assert.assertNull(gotPerson)
        }
    }

    @Test
    fun updatingShouldBeSuccessful() {
        runBlocking {
            val person = createTestPerson()
            val updatedPerson = createUpdatedPerson(person)

            val addedPerson = personService.addPerson(person)
            personService.updatePerson(updatedPerson)
            val gotPerson = personService.findByEmail(addedPerson.email)

            Assert.assertEquals(gotPerson, updatedPerson)

            personService.deletePersonByEmail(addedPerson.email)
        }
    }

    private fun createTestPerson(): PersonED {
        return PersonED.Builder.createBuilder()
            .setEmail("test@test.test")
            .setPhoto("test")
            .setName("test")
            .setAge(0)
            .setMark(0)
            .setTags(null)
            .setMetro(null)
            .setMoney(null)
            .setRooms(null)
            .setDescription(null)
            .build();
    }

    private fun createUpdatedPerson(oldPerson: PersonED): PersonED {
        return PersonED.Builder.createBuilder()
            .setEmail(oldPerson.email)
            .setPhoto(oldPerson.photo)
            .setName(oldPerson.name)
            .setAge(oldPerson.age)
            .setMark(oldPerson.mark)
            .setTags(oldPerson.tags)
            .setMetro(oldPerson.metro)
            .setMoney(oldPerson.money)
            .setRooms(oldPerson.rooms)
            .setDescription(oldPerson.description)
            .build();
    }
}

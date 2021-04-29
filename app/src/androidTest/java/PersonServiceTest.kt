import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.dao.IPersonService
import ru.mail.polis.dao.PersonED
import ru.mail.polis.dao.PersonService

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
        return PersonED(
            "test@test.test", "test", "test", 0,
            0, null, null, null, null, null
        )
    }

    private fun createUpdatedPerson(oldPerson: PersonED): PersonED {
        return PersonED(
            oldPerson.email, "${oldPerson.photo}1", "${oldPerson.name}1",
            oldPerson.age?.plus(1), oldPerson.mark?.plus(1), null,
            null, null, null, null
        )
    }
}

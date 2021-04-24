import org.junit.Assert
import org.junit.Test
import ru.mail.polis.dao.IPersonService
import ru.mail.polis.dao.PersonED
import ru.mail.polis.dao.PersonService

class PersonServiceTest {
    val personService: IPersonService = PersonService()

    @Test
    fun personShouldBeAddedSucessfull() {
        val emailOnlyPerson = createTestPerson();

        val addedPerson = personService.addPerson(emailOnlyPerson)
        val gotPerson = personService.findByEmail(addedPerson.email)

        Assert.assertNotNull(gotPerson)
        Assert.assertEquals(addedPerson, gotPerson)
        personService.deletePersonByEmail(addedPerson.email)
    }

//    @Test
//    fun personShouldBeAddedSucessfull() {
//        val emailOnlyPerson = PersonED(
//            "test@test.test", null, null, null,
//            null, null, null, null, null, null
//        )
//
//        val addedPerson = personService.addPerson(emailOnlyPerson)
//        val gotPerson = personService.findByEmail(addedPerson.email)
//        personService.deletePersonByEmail(addedPerson.email)
//
//        Assert.assertNotNull(gotPerson)
//        Assert.assertEquals(addedPerson, gotPerson)
//    }

    private fun createTestPerson(): PersonED {
        return PersonED(
            "test@test.test", "test", "test", 0,
            0, null, null, null, null, null
        )
    }
}
package ru.mail.polis.unit

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonService

class PersonViewServiceTest {
    private val personService: IPersonService = PersonService.getInstance()

    @Test
    fun personShouldBeAddedSuccessful() {
        runBlocking {
            val person = TestData.createTestPerson()

            val addedPerson = personService.addPerson(person)
            val gotPerson = personService.findByEmail(addedPerson.email!!)

            Assert.assertNotNull(gotPerson)
            Assert.assertEquals(addedPerson, gotPerson)

            personService.deletePersonByEmail(addedPerson.email!!)
        }
    }

    @Test
    fun deletingShouldBeSuccessful() {
        runBlocking {
            val person = TestData.createTestPerson()

            val addedPerson = personService.addPerson(person)
            personService.deletePersonByEmail(addedPerson.email!!)

            val gotPerson = personService.findByEmail(addedPerson.email!!)

            Assert.assertNull(gotPerson)
        }
    }

    @Test
    fun updatingShouldBeSuccessful() {
        runBlocking {
            val person = TestData.createTestPerson()
            val updatedPerson = TestData.createUpdatedPerson(person)

            val addedPerson = personService.addPerson(person)
            personService.updatePerson(updatedPerson)
            val gotPerson = personService.findByEmail(addedPerson.email!!)

            Assert.assertEquals(gotPerson, updatedPerson)

            personService.deletePersonByEmail(addedPerson.email!!)
        }
    }
}

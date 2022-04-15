package ru.mail.polis.viewModels

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

class SelfDefinitionViewModelTest {
    private val selfDefinitionViewModel = SelfDefinitionViewModel()
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val personService: IPersonService = PersonService.getInstance()


    @Test
    fun checkApartmentExist() {
        runBlocking {
            val apartment = TestData.createTestApartment()
            apartmentService.addApartment(apartment)

            val fetchApartment = selfDefinitionViewModel.fetchApartment(apartment.email!!)

            assertTrue(fetchApartment?.equals(apartment) ?: false)

            deleteApartment(apartment)
        }
    }

    @Test
    fun checkApartmentNotExist() {
        runBlocking {
            val apartment = TestData.createTestApartment()

            val fetchApartment = selfDefinitionViewModel.fetchApartment(apartment.email!!)

            assertTrue(fetchApartment == null)

        }


    }

    @Test
    fun checkPersonExist() {
        runBlocking {
            val person = TestData.createTestPerson()
            personService.addPerson(person)

            val fetchPerson = selfDefinitionViewModel.fetchPerson(person.email!!)

            assertTrue(fetchPerson?.equals(person) ?: false)

            deletePerson(person)
        }
    }

    @Test
    fun checkPersonNotExist() {
        runBlocking {
            val person = TestData.createTestPerson()

            val fetchPerson = selfDefinitionViewModel.fetchPerson(person.email!!)

            assertTrue(fetchPerson == null)
        }

    }

    private suspend fun deleteApartment(apartmentEd: ApartmentED) {
        apartmentService.deleteApartmentByEmail(apartmentEd.email!!)
    }

    private suspend fun deletePerson(personED: PersonED) {
        personService.deletePersonByEmail(personED.email!!)
    }
}
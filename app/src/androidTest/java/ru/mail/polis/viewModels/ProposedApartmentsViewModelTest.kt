package ru.mail.polis.viewModels

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class ProposedApartmentsViewModelTest {
    private val proposedApartmentsViewModel = ProposedApartmentsViewModel()
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val proposeService: IProposeService = ProposeService.getInstance()
    private val userService: IUserService = UserService()

    @Test
    fun fetchUsersTest() {
        runBlocking {

            val users = TestData.getTwoUsers()

            users.forEach {
                userService.addUser(it)
            }

            val fetchUsers =
                proposedApartmentsViewModel.fetchUsers(users.map { it.email!! }.toSet())

            var correctUsersList = true

            fetchUsers.forEach {
                if (!users.contains(it)) {
                    correctUsersList = false
                }
            }

            assertTrue(correctUsersList)

            deleteUsers(users)
        }
    }

    @Test
    fun emptyFetchUsers() {
        runBlocking {

            val users = TestData.getTwoUsers()


            val fetchUsers =
                proposedApartmentsViewModel.fetchUsers(users.map { it.email!! }.toSet())

            assertTrue(fetchUsers.isEmpty())
        }
    }

    @Test
    fun fetchProposeByRenterEmailTest() {
        runBlocking {
            val propose = TestData.getPropose()

            proposeService.createPropose(propose)

            val fetchProposeByRenterEmailList =
                proposedApartmentsViewModel.fetchProposeByRenterEmail(propose.renterEmail!!)

            assertFalse(fetchProposeByRenterEmailList.isEmpty())
            assertTrue(fetchProposeByRenterEmailList.size == 1)

            deletePropose(propose)
        }
    }

    @Test
    fun emptyFetchProposeByRenterEmailTest() {
        runBlocking {
            val propose = TestData.getPropose()


            val fetchProposeByRenterEmailList =
                proposedApartmentsViewModel.fetchProposeByRenterEmail(propose.renterEmail!!)

            assertTrue(fetchProposeByRenterEmailList.isEmpty())
        }
    }

    @Test
    fun fetchApartmentsByRenterEmailTest() {
        runBlocking {

            val apartment = TestData.createTestApartment()
            val propose = TestData.getPropose()
            propose.ownerEmail = apartment.email

            apartmentService.addApartment(apartment)
            proposeService.createPropose(propose)

            val fetchApartmentsByRenterEmailList =
                proposedApartmentsViewModel.fetchApartmentsByRenterEmail(propose.renterEmail!!)

            assertFalse(fetchApartmentsByRenterEmailList.isEmpty())
            assertTrue(fetchApartmentsByRenterEmailList.size == 1)

            deletePropose(propose)
            deleteApartment(apartment)
        }
    }

    @Test
    fun emptyFetchApartmentsByRenterEmailTest() {
        runBlocking {
            val apartment = TestData.createTestApartment()

            val fetchApartmentsByRenterEmailList =
                proposedApartmentsViewModel.fetchApartmentsByRenterEmail(apartment.email!!)

            assertTrue(fetchApartmentsByRenterEmailList.isEmpty())
        }
    }

    private suspend fun deleteUsers(users: List<UserED>) {
        users.forEach {
            userService.deleteUser(it.email!!)
        }
    }

    private suspend fun deletePropose(propose: ProposeED) {
        proposeService.deletePropose(propose)
    }

    private suspend fun deleteApartment(apartment: ApartmentED) {
        apartmentService.deleteApartmentByEmail(apartment.email!!)
    }

}
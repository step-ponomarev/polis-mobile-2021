package ru.mail.polis.viewModels

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserService

class ListOfPeopleViewModelTest {
    private val listOfPeopleViewModel = ListOfPeopleViewModel()
    private val personService: IPersonService = PersonService.getInstance()
    private val userService: IUserService = UserService()

    @Test
    fun fetchUsersTest() {
        runBlocking {

            val list = TestData.getTwoUserList()

            list.forEach {
                userService.addUser(it)
            }

            val fetchPeople = listOfPeopleViewModel.fetchUsers(list.map { it.email!! }.toSet())


            var counter = 0;

            list.forEach {
                fetchPeople.contains(it)
                counter++
            }

            assertTrue(!fetchPeople.isEmpty())
            assertTrue(counter == list.size)


            list.forEach {
                userService.deleteUser(it.email!!)
            }
        }
    }


    @Test
    fun fetchPeopleTest() {
        runBlocking {

            val list = TestData.getTwoPersonList()

            list.forEach {
                personService.addPerson(it)
            }

            val fetchPeople = listOfPeopleViewModel.fetchPeople()


            var counter = 0;

            list.forEach {
                fetchPeople.contains(it)
                counter++
            }

            assertTrue(!fetchPeople.isEmpty())
            assertTrue(counter == list.size)


            list.forEach {
                personService.deletePersonByEmail(it.email!!)
            }
        }
    }


}
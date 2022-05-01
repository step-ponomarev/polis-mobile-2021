package ru.mail.polis.unit

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.TestData
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserService

class UserServiceTest {

    private val userService: IUserService = UserService()

    @Test
    fun addUserTest() {
        runBlocking {
            val user = TestData.getUser()

            userService.addUser(user)

            val exist = userService.isExist(user.email!!)

            Assert.assertTrue(exist)

            userService.deleteUser(user.email!!)
        }
    }

    @Test
    fun deleteUserTest() {
        runBlocking {
            val user = TestData.getUser()

            userService.addUser(user)

            var exist = userService.isExist(user.email!!)

            Assert.assertTrue(exist)

            userService.deleteUser(user.email!!)

            exist = userService.isExist(user.email!!)

            Assert.assertFalse(exist)
        }
    }

    @Test
    fun updateUserTest() {
        runBlocking {
            val user = TestData.getUser()
            val updatedUser = TestData.getUpdatedUser()

            val email = user.email!!

            userService.addUser(user)

            userService.updateUserByEmail(email, updatedUser)

            val updatedUserFromDb = userService.findUserByEmail(email)

            Assert.assertFalse(updatedUserFromDb?.equals(user) ?: throw IllegalStateException())
            Assert.assertTrue(updatedUserFromDb.equals(updatedUser))

            userService.deleteUser(email)
        }
    }

    @Test
    fun existUserTest() {
        runBlocking {
            val user = TestData.getUser()

            userService.addUser(user)

            Assert.assertTrue(userService.isExist(user.email!!))

            userService.deleteUser(user.email!!)
        }
    }

    @Test
    fun findUserByEmailTest() {
        runBlocking {
            val user = TestData.getUser()
            val email = user.email ?: return@runBlocking

            userService.addUser(user)

            val findUserByEmail = userService.findUserByEmail(email)

            Assert.assertTrue(findUserByEmail?.equals(user) ?: throw IllegalStateException())


            userService.deleteUser(email)
        }
    }

    @Test
    fun findUsersByEmail() {
        runBlocking {
            val tenUsersList = TestData.getTenUsersList()

            tenUsersList.forEach {
                userService.addUser(it)
            }

            val emailsSet: Set<String> = tenUsersList.map { user -> user.email!! }.toSet()

            val findUsersByEmails = userService.findUsersByEmails(emailsSet)

            var isExist = true;

            findUsersByEmails.forEach {
                if (!tenUsersList.contains(it)) {
                    isExist = false;
                }
            }

            Assert.assertTrue(isExist)


            tenUsersList.forEach {
                userService.deleteUser(it.email ?: throw IllegalStateException())
            }
        }
    }
}
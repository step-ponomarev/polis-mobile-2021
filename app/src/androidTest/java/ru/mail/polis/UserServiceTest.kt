package ru.mail.polis

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class UserServiceTest {

    private val userService: IUserService = UserService()

    @Test
    fun addUserTest() {
        runBlocking {
            val user = getUser()

            userService.addUser(user)

            val exist = userService.isExist(user.email!!)

            Assert.assertTrue(exist)

            userService.deleteUser(user.email!!)
        }
    }

    @Test
    fun deleteUserTest() {
        runBlocking {
            val user = getUser()

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
            val user = getUser()
            val updatedUser = getUpdatedUser()

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
            val user = getUser()

            userService.addUser(user)

            Assert.assertTrue(userService.isExist(user.email!!))

            userService.deleteUser(user.email!!)
        }
    }

    @Test
    fun findUserByEmailTest() {
        runBlocking {
            val user = getUser()
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
            val tenUsersList = getTenUsersList()

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

    private fun getTenUsersList(): List<UserED> {
        val userList = ArrayList<UserED>()
        for (i in 1..10) {
            userList.add(
                UserED(
                    email = "testUserEmail@mail.ru" + i,
                    name = "Ilya" + i,
                    surname = "Tester" + i,
                    phone = "8800553535",
                    age = 21,
                )
            )
        }

        return userList
    }

    private fun getUser(): UserED {
        return UserED(
            email = "testUserEmail@mail.ru",
            name = "Ilya",
            surname = "Tester",
            phone = "8800553535",
            age = 21,
        )
    }

    private fun getUpdatedUser(): UserED {
        val user = getUser()

        user.name = "Dima"
        user.surname = "Sosister"
        user.age = 22


        return user
    }

}
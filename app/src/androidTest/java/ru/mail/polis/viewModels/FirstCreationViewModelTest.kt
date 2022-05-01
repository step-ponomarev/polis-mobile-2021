package ru.mail.polis.viewModels

import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.mail.polis.R
import ru.mail.polis.TestData
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class FirstCreationViewModelTest {
    private val firstCreationViewModel = FirstCreationViewModel()
    private val userService: IUserService = UserService()
    private val photoUriService: IPhotoUriService = PhotoUriService()

    @Test
    fun test() {
        runBlocking {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            val user = TestData.getUser()

            val bitmap = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.picture)


            firstCreationViewModel.addUser(user, bitmap)


            var findUserByEmail = userService.findUserByEmail(user.email!!)

            assertTrue(findUserByEmail?.equals(user) ?: false)

            delete(user.email!!)

            findUserByEmail = userService.findUserByEmail(user.email!!)

            assertTrue(findUserByEmail == null)
        }
    }

    private suspend fun delete(email: String) {
        userService.deleteUser(email)
    }


}
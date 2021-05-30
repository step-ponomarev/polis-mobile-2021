package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationKeeperException
import java.lang.IllegalStateException

class SettingsViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val userService: IUserService = UserService()
    private val photoUriService: IPhotoUriService = PhotoUriService()

    private var userED = MutableLiveData<UserED>()

    @Throws(NotificationKeeperException::class)
    suspend fun getUserInfo(email: String) {
        val user = withContext(Dispatchers.IO) {
            userService.findUserByEmail(email)
        } ?: throw IllegalStateException("Fetched user is null")

        withContext(Dispatchers.Main) {
            userED.value = user
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun uploadPhoto(path: String, bitmap: Bitmap): String {
        try {
            return withContext(Dispatchers.IO) {
                photoUriService.saveImage(path, Converter.bitmapToInputStream(bitmap))
            }.toString()
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Photo was not uploaded path: $path",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun updateUser(user: UserED) {
        try {
            val updatedUser = withContext(Dispatchers.IO) {
                userService.updateUserByEmail(user.email, user)
            } ?: throw IllegalStateException("Updated user is null")

            withContext(Dispatchers.Main) {
                userED.value = updatedUser
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed user updating $user",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun checkApartmentExist(email: String): Boolean {
        try {
            return apartmentService.isExist(email)
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed checking user existence by email: $email",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    fun getUser(): LiveData<UserED> = userED
}

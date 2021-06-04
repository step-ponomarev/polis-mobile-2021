package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationKeeperException

class SettingsViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val personService: IPersonService = PersonService.getInstance()
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
    suspend fun updateUser(user: UserED, bitmap: Bitmap?) {
        val pathString = "${Collections.USER.collectionName}Photos/${user.email}-photo.jpg"

        try {
            if (user.photo == null && bitmap != null) {
                withContext(Dispatchers.IO) {
                    val url =
                        photoUriService.saveImage(pathString, Converter.bitmapToInputStream(bitmap))
                    user.photo = url.toString()
                }
            }

            val updatedUser = withContext(Dispatchers.IO) {
                userService.updateUserByEmail(user.email!!, user)
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

    @Throws(NotificationKeeperException::class)
    suspend fun checkAdvertExist(email: String): Boolean {
        try {
            return personService.isExist(email)
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed to find advert with email: $email",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    fun getUser(): LiveData<UserED> = userED
}

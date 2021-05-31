package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.DaoResult
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
    private val userED: MutableStateFlow<UserED> = MutableStateFlow(UserED())
    val user: StateFlow<UserED> = userED

    @Throws(NotificationKeeperException::class)
    fun init(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userService.findUserByEmail(email)) {
                is DaoResult.Success ->
                    userED.value = result.data
                        ?: throw IllegalStateException("Null user by email: $email")
                is DaoResult.Error -> throw IllegalStateException(
                    "Null user by email: $email",
                    result.e
                )
            }
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
}

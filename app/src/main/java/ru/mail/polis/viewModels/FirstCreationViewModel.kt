package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationKeeperException

class FirstCreationViewModel : ViewModel() {

    private val userService: IUserService = UserService()
    private val photoUriService: IPhotoUriService = PhotoUriService()

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
    suspend fun addUser(userED: UserED) {
        try {
            withContext(Dispatchers.IO) {
                userService.addUser(userED)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "User was not added: $userED",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }
}

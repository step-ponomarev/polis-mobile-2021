package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.R
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.exception.DaoException
import ru.mail.polis.exception.NotificationKeeperException

class SettingsViewModel : ViewModel() {

    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val userService: IUserService = UserService()
    private val photoUriService: IPhotoUriService = PhotoUriService()

    private var userED = MutableLiveData<UserED>()

    fun getUserInfo(email: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val user = userService.findUserByEmail(email)

            withContext(Dispatchers.Main) {
                userED.value = user!!
            }
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

            withContext(Dispatchers.IO) {
                userED.value = userService.updateUserByEmail(user.email!!, user)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed user updating $user",
                e,
                R.string.error_bad_internet
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
                R.string.error_bad_internet
            )
        }
    }

    fun getUser(): LiveData<UserED> = userED
}

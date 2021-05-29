package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.IPhotoUriService
import ru.mail.polis.dao.PhotoUriService
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

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

    fun updateUser(user: UserED, bitmap: Bitmap?) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = async {
                if (user.photo == null && bitmap != null) {
                    val url = withContext(Dispatchers.IO) {
                        val pathString =
                            "${Collections.USER.collectionName}Photos/${user.email}-photo.jpg"
                        photoUriService.saveImage(pathString, Converter.bitmapToInputStream(bitmap))
                    }

                    user.photo = url.toString()
                }
            }
            task.await()

            withContext(Dispatchers.Main) {
                userED.value = userService.updateUserByEmail(user.email!!, user)
            }
        }
    }

    suspend fun checkApartmentExist(email: String): Boolean {

        return apartmentService.isExist(email)
    }

    fun getUser(): LiveData<UserED> = userED
}

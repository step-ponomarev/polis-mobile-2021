package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.IPhotoUriService
import ru.mail.polis.dao.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class FirstCreationViewModel : ViewModel() {

    private val userService: IUserService = UserService()
    private val photoUriService: IPhotoUriService = PhotoUriService()

    fun addUser(userED: UserED, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {

            val url = withContext(Dispatchers.IO) {
                val pathString =
                    "${Collections.USER.collectionName}Photos/${userED.email}-photo.jpg"
                photoUriService.saveImage(pathString, Converter.bitmapToInputStream(bitmap))
            }

            withContext(Dispatchers.IO) {
                userED.photo = url.toString()
                userService.addUser(userED)
            }
        }
    }
}
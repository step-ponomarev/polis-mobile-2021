package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.IPhotoUriService
import ru.mail.polis.dao.PhotoUriService
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class ApartmentViewModel : ViewModel() {
    private val userService: IUserService = UserService()
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val photoUriService: IPhotoUriService = PhotoUriService()
    val list = LinkedHashSet<Bitmap>()

    suspend fun addApartment(apartmentED: ApartmentED): ApartmentED {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            apartmentED.photosUrls = getUrlList()
            apartmentService.addApartment(apartmentED)
        }
    }

    suspend fun fetchUser(email: String): UserED? {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            userService.findUserByEmail(email)
        }
    }

    suspend fun updateApartment(apartmentED: ApartmentED): ApartmentED {
        return withContext(Dispatchers.IO) {
            apartmentED.photosUrls = getUrlList()
            apartmentService.updateApartment(apartmentED)
        }
    }

    suspend fun getApartmentByEmail(email: String): ApartmentED? {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            apartmentService.findByEmail(email)
        }
    }

    private suspend fun getUrlList(): List<String> {
        val urlList = ArrayList<String>()

        list.forEach {

            val pathString =
                "${Collections.APARTMENT.collectionName}Photos/${getRandomNameForFile()}.jpg"

            val url =
                photoUriService.saveImage(pathString, Converter.bitmapToInputStream(it))
            urlList.add(url.toString())
        }

        return urlList
    }

    private fun getRandomNameForFile(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString = (1..50)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        return randomString
    }
}

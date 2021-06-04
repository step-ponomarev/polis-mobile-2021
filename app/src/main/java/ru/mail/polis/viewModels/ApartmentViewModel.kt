package ru.mail.polis.viewModels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationKeeperException
import kotlin.jvm.Throws
import kotlin.random.Random

class ApartmentViewModel : ViewModel() {
    private val userService: IUserService = UserService()
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val photoUriService: IPhotoUriService = PhotoUriService()
    private val list = LinkedHashSet<Bitmap>()

    companion object {
        private const val NAME = "ApartmentViewModel"
    }

    fun addImage(image: Bitmap) {
        list.add(image)
    }

    fun removeImage(bitmap: Bitmap) {
        list.remove(bitmap)
    }

    fun getImageList(): Collection<Bitmap> {
        return list
    }

    @Throws(NotificationKeeperException::class)
    suspend fun addApartment(apartmentED: ApartmentED): ApartmentED {
        try {
            return withContext(Dispatchers.IO) {
                apartmentED.photosUrls = getUrlList()
                apartmentService.addApartment(apartmentED)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failure adding apartment: $apartmentED",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun fetchUser(email: String): UserED? {
        try {
            return withContext(Dispatchers.IO) {
                userService.findUserByEmail(email)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failure fetching user with email: $email",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun updateApartment(apartmentED: ApartmentED): ApartmentED {
        try {
            return withContext(Dispatchers.IO) {
                apartmentED.photosUrls = getUrlList()
                apartmentService.updateApartment(apartmentED)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failure updating apartment: $apartmentED",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    @Throws(NotificationKeeperException::class)
    suspend fun getApartmentByEmail(email: String): ApartmentED? {
        try {
            return withContext(Dispatchers.IO) {
                apartmentService.findByEmail(email)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failure fetching apartment with email: $email",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    private suspend fun getUrlList(): List<String> {
        val urlList = ArrayList<String>()

        for (bitMap in list) {
            try {
                val pathString =
                    "${Collections.APARTMENT.collectionName}Photos/${getRandomNameForFile()}.jpg"

                val url =
                    photoUriService.saveImage(pathString, Converter.bitmapToInputStream(bitMap))
                urlList.add(url.toString())
            } catch (e: DaoException) {
                Log.w(NAME, "Photo uploading failed: $e")
            }
        }

        return urlList
    }

    private fun getRandomNameForFile(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..50)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}

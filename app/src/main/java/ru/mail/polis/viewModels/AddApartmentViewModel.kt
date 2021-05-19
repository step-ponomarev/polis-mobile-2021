package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.IPhotoUriService
import ru.mail.polis.dao.PhotoUriService
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService

class AddApartmentViewModel : ViewModel() {

    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val photoUriService: IPhotoUriService = PhotoUriService()
    val list = LinkedHashSet<Bitmap>()

    fun addApartment(apartmentED: ApartmentED) {

        viewModelScope.launch(Dispatchers.IO) {
            apartmentED.photosUrls = getUrlList()
            apartmentService.addApartment(apartmentED)
        }
    }

    private suspend fun getUrlList(): List<String> {
        val urlList = ArrayList<String>()

        list.forEach {
            val url =
                photoUriService.saveImage(Collections.APARTMENT, Converter.bitmapToInputStream(it))
            urlList.add(url.toString())
        }

        return urlList
    }
}

package ru.mail.polis.unit

import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.R
import ru.mail.polis.converter.Converter
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.photo.IPhotoUriService
import ru.mail.polis.dao.photo.PhotoUriService

class PhotoUriServiceTest {

    private val photoService: IPhotoUriService = PhotoUriService()
    private val pathString =
        "apartmentPhotos/testApartmentPhoto.jpg"

    @Test
    fun saveImageTest() {
        runBlocking {
            val imageByteArray = getImageByteArray()

            photoService.saveImage(pathString, imageByteArray)

            val existImage = photoService.isExistImage(pathString)

            Assert.assertTrue(existImage)

            photoService.deleteImage(pathString)
        }
    }


    @Test
    fun uploadTenImagesAndCheckThatExist() {
        runBlocking {
            val paths = getFivePaths()
            val imageArray = getImageByteArray()

            paths.forEach {
                photoService.saveImage(it, imageArray)
            }

            var allExist = true;
            paths.forEach {
                val existImage = photoService.isExistImage(it)
                if (!existImage) {
                    allExist = false;
                }
            }

            Assert.assertTrue(allExist)


            paths.forEach {
                photoService.deleteImage(it)
            }

        }
    }


    @Test
    fun deleteImageTest() {
        runBlocking {
            val imageByteArray = getImageByteArray()

            photoService.saveImage(pathString, imageByteArray)

            var existImage = photoService.isExistImage(pathString)

            Assert.assertTrue(existImage)

            photoService.deleteImage(pathString)

            existImage = photoService.isExistImage(pathString)

            Assert.assertFalse(existImage)
        }
    }

    @Test
    fun tryToDeleteTwice() {
        runBlocking {
            val imageByteArray = getImageByteArray()

            photoService.saveImage(pathString, imageByteArray)

            var existImage = photoService.isExistImage(pathString)

            Assert.assertTrue(existImage)

            photoService.deleteImage(pathString)
            var isAlreadyDeleted = false;
            try {
                photoService.deleteImage(pathString)
            } catch (e: DaoException) {
                isAlreadyDeleted = true;
            }


            Assert.assertTrue(isAlreadyDeleted)
        }
    }

    private fun getFivePaths(): List<String> {
        val listPaths = ArrayList<String>()

        for (i in 1..5) {
            listPaths.add("apartmentPhotos/testApartmentPhoto${i}.jpg")
        }

        return listPaths
    }

    private fun getImageByteArray(): ByteArray {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val icon = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.picture);


        val firstBitmap = icon


        return Converter.bitmapToInputStream(firstBitmap)
    }
}
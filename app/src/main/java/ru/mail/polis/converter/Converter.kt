package ru.mail.polis.converter

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

object Converter {

    private const val BITMAP_QUALITY = 100

    fun bitmapToInputStream(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUALITY, baos)
        return baos.toByteArray()
    }
}

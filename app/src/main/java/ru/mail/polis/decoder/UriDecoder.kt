package ru.mail.polis.decoder

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build

class UriDecoder(private val contentResolver: ContentResolver) {
    fun decode(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val image =
                ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(image)
        } else {
            val imageStream = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(imageStream)
        }
    }
}

package ru.mail.polis.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.core.content.ContentProviderCompat

object DecoderFactory {
    fun getImageDecoder(contentResolver: ContentResolver): Decoder {
        return UriDecoder(contentResolver)
    }
}
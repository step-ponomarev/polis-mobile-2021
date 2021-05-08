package ru.mail.polis.utils

import android.content.ContentResolver

object DecoderFactory {
    fun getImageDecoder(contentResolver: ContentResolver): Decoder {
        return UriDecoder(contentResolver)
    }
}

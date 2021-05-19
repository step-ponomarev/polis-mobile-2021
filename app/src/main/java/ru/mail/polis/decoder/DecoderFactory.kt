package ru.mail.polis.decoder

import android.content.ContentResolver

object DecoderFactory {
    fun getImageDecoder(contentResolver: ContentResolver): UriDecoder {
        return UriDecoder(contentResolver)
    }
}

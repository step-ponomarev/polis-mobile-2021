package ru.mail.polis.decoder

import android.content.ContentResolver

object DecoderFactory {
    fun getImageDecoder(contentResolver: ContentResolver): Decoder {
        return UriDecoder(contentResolver)
    }
}

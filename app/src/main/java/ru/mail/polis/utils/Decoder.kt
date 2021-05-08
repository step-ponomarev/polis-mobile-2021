package ru.mail.polis.utils

import android.graphics.Bitmap
import android.net.Uri

interface Decoder {
    fun decode(uri: Uri): Bitmap
}

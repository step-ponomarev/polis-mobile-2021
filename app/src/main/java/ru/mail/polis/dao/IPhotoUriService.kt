package ru.mail.polis.dao

import android.net.Uri

interface IPhotoUriService {
    suspend fun saveImage(pathString: String, byteArray: ByteArray): Uri
}

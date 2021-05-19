package ru.mail.polis.dao

import android.net.Uri

interface IPhotoUriService {
    suspend fun saveImage(collections: Collections, byteArray: ByteArray): Uri
}

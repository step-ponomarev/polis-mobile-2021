package ru.mail.polis.dao.photo

import android.net.Uri
import ru.mail.polis.dao.DaoException

interface IPhotoUriService {
    @Throws(DaoException::class)
    suspend fun saveImage(pathString: String, byteArray: ByteArray): Uri

    @Throws(DaoException::class)
    suspend fun isExistImage(pathString: String): Boolean

    @Throws(DaoException::class)
    suspend fun deleteImage(pathString: String)
}

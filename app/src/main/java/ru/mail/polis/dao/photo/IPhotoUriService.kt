package ru.mail.polis.dao.photo

import android.net.Uri
import ru.mail.polis.exception.DaoException

interface IPhotoUriService {
    @Throws(DaoException::class)
    suspend fun saveImage(pathString: String, byteArray: ByteArray): Uri
}

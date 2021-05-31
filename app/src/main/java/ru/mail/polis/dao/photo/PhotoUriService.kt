package ru.mail.polis.dao.photo

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mail.polis.dao.DaoException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PhotoUriService : IPhotoUriService {

    private val storage = FirebaseStorage.getInstance()

    @Throws(DaoException::class)
    override suspend fun saveImage(pathString: String, byteArray: ByteArray): Uri {
        return suspendCancellableCoroutine { coroutine ->
            val ref = storage.reference.child(pathString)
            val uploadTask = ref.putBytes(byteArray)

            uploadTask.continueWithTask { ref.downloadUrl }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        coroutine.resume(task.result!!)
                    }
                }.addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure image fetching path: $pathString",
                            it
                        )
                    )
                }
        }
    }
}

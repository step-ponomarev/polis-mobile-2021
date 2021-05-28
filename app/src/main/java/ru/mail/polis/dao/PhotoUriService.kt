package ru.mail.polis.dao

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PhotoUriService : IPhotoUriService {

    private val storage = FirebaseStorage.getInstance()

    override suspend fun saveImage(pathString: String, byteArray: ByteArray): Uri {

        return suspendCancellableCoroutine { coroutine ->

            val ref =
                storage.reference.child(pathString)

            val uploadTask = ref.putBytes(byteArray)

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    coroutine.resume(task.result!!)
                }
            }.addOnFailureListener {
                coroutine.cancel(it)
            }
        }
    }
}

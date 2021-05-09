package ru.mail.polis.dao

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PhotoUriService : IPhotoUriService {

    private val storage = FirebaseStorage.getInstance();

    override suspend fun saveImage(byteArray: ByteArray): Uri {

        return suspendCancellableCoroutine { coroutine ->

            val ref = storage.reference.child("apartmentPhotos/${getRandomNameForFile()}.jpg")

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
            }

        }
    }

    private fun getRandomNameForFile(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString = (1..50)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        return randomString
    }
}
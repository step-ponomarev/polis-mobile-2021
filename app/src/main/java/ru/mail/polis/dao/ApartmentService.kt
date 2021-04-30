package ru.mail.polis.dao

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ApartmentService private constructor() : IApartmentService {
    private val db: FirebaseFirestore = Firebase.firestore
    private val apartmentCollection: CollectionReference =
        db.collection(Collections.APARTMENT.collectionName)

    companion object {
        private var INSTANCE: IApartmentService? = null

        fun getInstance(): IApartmentService {
            if (INSTANCE == null) {
                INSTANCE = ApartmentService()
            }

            return INSTANCE!!
        }
    }

    override suspend fun findByEmail(email: String): ApartmentED? {
        val apartmentRef = apartmentCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            apartmentRef.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObject(ApartmentED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException("Filed fetching apartment with email: $email", it)
                    )
                }
        }
    }

    override suspend fun findAll(): List<ApartmentED> {
        return suspendCancellableCoroutine { coroutine ->
            apartmentCollection.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObjects(ApartmentED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException("Filed fetching apartment list", it)
                    )
                }
        }
    }

    override suspend fun addApartment(apartment: ApartmentED): ApartmentED {
        return suspendCancellableCoroutine { coroutine ->
            apartmentCollection.document(apartment.email)
                .set(apartment)
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Failure adding apartments with email: ${apartment.email}",
                            it
                        )
                    )
                }
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful adding apartments with email: ${apartment.email}"
                    )

                    coroutine.resume(apartment)
                }
        }
    }

    override suspend fun updateApartment(apartment: ApartmentED): ApartmentED {
        val apartmentRef = apartmentCollection.document(apartment.email)

        return suspendCancellableCoroutine { coroutine ->
            apartmentRef.update(apartmentToMap(apartment))
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Failure updating person with email: ${apartment.email}",
                            it
                        )
                    )
                }
                .addOnSuccessListener {
                    coroutine.resume(apartment)
                }
        }
    }

    override suspend fun deleteApartmentByEmail(email: String) {
        val apartmentRef = apartmentCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            apartmentRef.delete()
                .addOnSuccessListener {
                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Failure deleting apartment with email: $email",
                            it
                        )
                    )
                }
        }
    }

    private fun apartmentToMap(apartment: ApartmentED): Map<String, Any?> {
        return mapOf(
            "email" to apartment.email,
            "ownerAvatar" to apartment.ownerAvatar,
            "ownerName" to apartment.ownerName,
            "ownerAge" to apartment.ownerAge,
            "metro" to apartment.metro,
            "roomCount" to apartment.roomCount,
            "apartmentSquare" to apartment.apartmentSquare,
            "apartmentCosts" to apartment.apartmentCosts,
            "photosUrls" to apartment.photosUrls
        )
    }
}

package ru.mail.polis.dao.apartments

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.core.FirestoreClient
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.DaoException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.jvm.Throws

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

    @Throws(DaoException::class)
    override suspend fun findByEmail(email: String): ApartmentED? {
        val apartmentRef = apartmentCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            apartmentRef.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObject(ApartmentED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException("Filed fetching apartment with email: $email", it)
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun findByEmails(emailList: Set<String>): List<ApartmentED> {
        return suspendCancellableCoroutine { coroutine ->
            apartmentCollection.whereIn("email", emailList.toList())
                .get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObjects(ApartmentED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException("Filed fetching apartments by emails: $emailList", it)
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun findAll(): List<ApartmentED> {
        return suspendCancellableCoroutine { coroutine ->
            apartmentCollection.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObjects(ApartmentED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException("Filed fetching apartment list", it)
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun addApartment(apartment: ApartmentED): ApartmentED {
        println(FirebaseApp.getInstance())
        println(Firebase.firestore.firestoreSettings.isPersistenceEnabled)
        println(Firebase.firestore.app.options)
        return suspendCancellableCoroutine { coroutine ->
            apartmentCollection.document(apartment.email!!)
                .set(apartment)
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful adding apartments with email: ${apartment.email}"
                    )

                    coroutine.resume(apartment)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure adding apartments with email: ${apartment.email}",
                            it
                        )
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun updateApartment(apartment: ApartmentED): ApartmentED {
        val apartmentRef = apartmentCollection.document(apartment.email!!)

        return suspendCancellableCoroutine { coroutine ->
            apartmentRef.update(apartmentToMap(apartment))
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
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

    @Throws(DaoException::class)
    override suspend fun deleteApartmentByEmail(email: String) {
        val apartmentRef = apartmentCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            apartmentRef.delete()
                .addOnSuccessListener {
                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure deleting apartment with email: $email",
                            it
                        )
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun isExist(email: String): Boolean {
        return suspendCancellableCoroutine { coroutine ->

            apartmentCollection.document(email)
                .get()
                .addOnSuccessListener { document ->
                    Log.i(
                        this::class.java.name,
                        "Successfully finding apartment with email: $email"
                    )
                    if (document.exists()) {
                        coroutine.resume(true)
                    } else {
                        coroutine.resume(false)
                    }
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure to find apartment with email: $email",
                            it
                        )
                    )
                }
        }
    }

    private fun apartmentToMap(apartment: ApartmentED): Map<String, Any?> {
        return mapOf(
            "email" to apartment.email,
            "phone" to apartment.phone,
            "metro" to apartment.metro,
            "roomCount" to apartment.roomCount,
            "apartmentSquare" to apartment.apartmentSquare,
            "apartmentCosts" to apartment.apartmentCosts,
            "photosUrls" to apartment.photosUrls
        )
    }
}

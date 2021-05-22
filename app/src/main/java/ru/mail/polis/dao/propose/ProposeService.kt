package ru.mail.polis.dao.propose

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mail.polis.dao.Collections
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ProposeService(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val proposeCollection: CollectionReference =
        db.collection(Collections.PROPOSE.collectionName)
) : IProposeService {
    companion object {
        private var INSTANCE: IProposeService? = null

        fun getInstance(): IProposeService {
            if (INSTANCE == null) {
                INSTANCE = ProposeService()
            }

            return INSTANCE!!
        }
    }

    override suspend fun findOwnerEmail(email: String): List<ProposeED> {
        return suspendCancellableCoroutine { coroutine ->
            proposeCollection.whereEqualTo("ownerEmail", email)
                .get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObjects(ProposeED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Filed fetching apartment propose by ownerEmail: $email",
                            it
                        )
                    )
                }
        }
    }

    override suspend fun findRenterEmail(email: String): List<ProposeED> {
        return suspendCancellableCoroutine { coroutine ->
            proposeCollection.whereEqualTo("renterEmail", email)
                .get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObjects(ProposeED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Filed fetching apartment propose by renterEmail: $email",
                            it
                        )
                    )
                }
        }
    }

    override suspend fun createPropose(proposeED: ProposeED): ProposeED {
        return suspendCancellableCoroutine { coroutine ->
            proposeCollection.document()
                .set(proposeED)
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Failure crating propose: $proposeED",
                            it
                        )
                    )
                }
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful creating propose: $proposeED"
                    )

                    coroutine.resume(proposeED)
                }
        }
    }
}

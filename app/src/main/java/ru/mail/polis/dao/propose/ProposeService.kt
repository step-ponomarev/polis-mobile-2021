package ru.mail.polis.dao.propose

import android.util.Log
import com.google.android.gms.common.util.CollectionUtils
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.DaoException
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
                        DaoException(
                            "Failed fetching apartment propose by ownerEmail: $email",
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
                        DaoException(
                            "Failed fetching apartment propose by renterEmail: $email",
                            it
                        )
                    )
                }
        }
    }

    override suspend fun checkProposeExist(ownerEmail: String, renterEmail: String): Boolean {
        return suspendCancellableCoroutine { coroutine ->
            proposeCollection.whereEqualTo("ownerEmail", ownerEmail)
                .whereEqualTo("renterEmail", renterEmail)
                .get()
                .addOnSuccessListener {
                    val list = it.toObjects(ProposeED::class.java)
                    coroutine.resume(!CollectionUtils.isEmpty(list))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failed fetching apartment propose by ownerEmail: $ownerEmail and renterEmail: $renterEmail",
                            it
                        )
                    )
                }
        }
    }

    override suspend fun updatePropose(proposeED: ProposeED): ProposeED {
        return suspendCancellableCoroutine { coroutine ->
            proposeCollection.whereEqualTo("ownerEmail", proposeED.ownerEmail)
                .whereEqualTo("renterEmail", proposeED.renterEmail)
                .get()
                .addOnSuccessListener {
                    if (it.documents.size == 1) {
                        it.documents[0].reference.update(proposeToMap(proposeED))
                        coroutine.resume(it.toObjects(ProposeED::class.java)[0])
                    } else if (it.documents.size == 0) {
                        coroutine.resumeWithException(
                            DaoException(
                                "Failed apdate propose by ownerEmail: ${proposeED.ownerEmail} and renterEmail: $proposeED.renterEmail", Exception())
                        )
                    }
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failed apdate propose by ownerEmail: ${proposeED.ownerEmail} and renterEmail: $proposeED.renterEmail",
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
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful creating propose: $proposeED"
                    )

                    coroutine.resume(proposeED)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure crating propose: $proposeED",
                            it
                        )
                    )
                }
        }
    }

    override suspend fun deletePropose(proposeED: ProposeED) {

        val proposeRef = getProposeRef(proposeED)

        val document = proposeCollection.document(proposeRef)

        return suspendCancellableCoroutine { coroutine ->
            document
                .delete()
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful deleting propose: $proposeED"
                    )

                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure deleting propose: $proposeED",
                            it
                        )
                    )
                }
        }
    }

    private suspend fun getProposeRef(proposeED: ProposeED): String {
        return suspendCancellableCoroutine { coroutine ->
            proposeCollection
                .whereEqualTo("ownerEmail", proposeED.ownerEmail)
                .whereEqualTo("renterEmail", proposeED.renterEmail)
                .whereEqualTo("status", proposeED.status)
                .get()
                .addOnSuccessListener {
                    coroutine.resume(it.documents[0].reference.id)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure getting propose ref: $proposeED",
                            it
                        )
                    )
                }
        }
    }

    private fun proposeToMap(proposeED: ProposeED): Map<String, Any?> {
        return mapOf(
            "ownerEmail" to proposeED.ownerEmail,
            "renterEmail" to proposeED.renterEmail,
            "status" to proposeED.status
        )
    }
}

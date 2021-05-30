package ru.mail.polis.dao.person

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.DaoException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.jvm.Throws

class PersonService private constructor() : IPersonService {
    private val db: FirebaseFirestore = Firebase.firestore
    private val personCollection: CollectionReference =
        db.collection(Collections.PERSON.collectionName)

    companion object {
        private var INSTANCE: IPersonService? = null

        fun getInstance(): IPersonService {
            if (INSTANCE == null) {
                INSTANCE = PersonService()
            }

            return INSTANCE!!
        }
    }

    @Throws(DaoException::class)
    override suspend fun findByEmail(email: String): PersonED? {
        val personRef = personCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            personRef.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObject(PersonED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException("Filed fetching person with email: $email", it)
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun findAll(): List<PersonED> {
        return suspendCancellableCoroutine { coroutine ->
            personCollection.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObjects(PersonED::class.java))
                }.addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException("Filed fetching all people", it)
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun addPerson(person: PersonED): PersonED {
        val data = Gson().toJson(person)

        return suspendCancellableCoroutine { coroutine ->
            personCollection.document(person.email)
                .set(data)
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful adding person with email: ${person.email}"
                    )

                    coroutine.resume(person)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure adding person with email: ${person.email}",
                            it
                        )
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun updatePerson(person: PersonED): PersonED {
        val personRef = personCollection.document(person.email)
        val data = Gson().toJson(person)
        val personUpdate = Gson().fromJson(data, Map::class.java) as Map<String, Any?>

        return suspendCancellableCoroutine { coroutine ->
            personRef.update(personUpdate)
                .addOnSuccessListener {
                    coroutine.resume(person)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure updating person with email: ${person.email}",
                            it
                        )
                    )
                }
        }
    }

    @Throws(DaoException::class)
    override suspend fun deletePersonByEmail(email: String) {
        val personRef = personCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            personRef.delete()
                .addOnSuccessListener {
                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure deleting person with email: $email",
                            it
                        )
                    )
                }
        }
    }
}

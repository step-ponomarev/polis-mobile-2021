package ru.mail.polis.dao.person

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mail.polis.dao.Collections
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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

    override suspend fun findByEmail(email: String): PersonED? {
        val personRef = personCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            personRef.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObject(PersonED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException("Filed fetching person with email: $email", it)
                    )
                }
        }
    }

    override suspend fun findAll(): List<PersonED> {
        return suspendCancellableCoroutine { coroutine ->
            personCollection.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObjects(PersonED::class.java))
                }.addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException("Filed fetching person list", it)
                    )
                }
        }
    }

    override suspend fun addPerson(person: PersonED): PersonED {
        return suspendCancellableCoroutine { coroutine ->
            personCollection.document(person.email!!)
                .set(personToMap(person))
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Failure adding person with email: ${person.email}",
                            it
                        )
                    )
                }
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful adding person with email: ${person.email}"
                    )

                    coroutine.resume(person)
                }
        }
    }

    override suspend fun updatePerson(person: PersonED): PersonED {
        val personRef = personCollection.document(person.email!!)

        return suspendCancellableCoroutine { coroutine ->
            personRef.update(personToMap(person))
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Failure updating person with email: ${person.email}",
                            it
                        )
                    )
                }
                .addOnSuccessListener {
                    coroutine.resume(person)
                }
        }
    }

    override suspend fun deletePersonByEmail(email: String) {
        val personRef = personCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            personRef.delete()
                .addOnSuccessListener {
                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Failure deleting person with email: $email",
                            it
                        )
                    )
                }
        }
    }

    private fun personToMap(person: PersonED): Map<String, Any?> {
        return mapOf(
            "email" to person.email,
            "photo" to person.photo,
            "name" to person.name,
            "age" to person.age,
            "tags" to person.tags,
            "metro" to person.metro,
            "moneyFrom" to person.moneyFrom,
            "moneyTo" to person.moneyTo,
            "rooms" to person.rooms,
            "description" to person.description
        )
    }
}

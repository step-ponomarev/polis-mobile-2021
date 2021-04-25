package ru.mail.polis.dao

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PersonService : IPersonService {
    private val db: FirebaseFirestore = Firebase.firestore
    private val personCollection: CollectionReference =
        db.collection(Collections.PERSON.collectionName)

    override suspend fun findByEmail(email: String): PersonED? {
        return suspendCancellableCoroutine { coroutine ->
            val personRef = personCollection.document(email)

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
            personCollection.get().addOnSuccessListener {
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
            personCollection.document(person.email)
                .set(personToMap(person)).addOnFailureListener {
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
        return suspendCancellableCoroutine { coroutine ->
            val personRef = personCollection.document(person.email)

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
        return suspendCancellableCoroutine { coroutine ->
            val personRef = personCollection.document(email)

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
            "mark" to person.mark,
            "tags" to person.tags,
            "metro" to person.metro,
            "money" to person.money,
            "rooms" to person.rooms,
            "description" to person.description
        )
    }
}

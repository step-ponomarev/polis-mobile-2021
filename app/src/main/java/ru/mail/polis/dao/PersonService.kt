package ru.mail.polis.dao

import android.util.Log
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import ru.mail.polis.metro.Metro
import java.lang.RuntimeException

class PersonService(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val personCollection: CollectionReference = db.collection(Collections.PERSON.collectionName)
) : IPersonService {
    override fun findByEmail(email: String): PersonED? {
        val personRef = personCollection.document(email)
        val data = await(personRef.get()).data

        return if (data == null) {
            data
        } else {
            mapToPerson(data)
        }
    }

    override fun findAll(): List<PersonED> {
        return personCollection.get().result?.toObjects() ?: emptyList()
    }

    override fun addPerson(person: PersonED): PersonED {
        await(
            personCollection.document(person.email)
                .set(personToMap(person)).addOnFailureListener {
                    throw RuntimeException(
                        "Failure adding person with email: ${person.email}",
                        it
                    )
                }
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful adding person with email: ${person.email}"
                    )
                }
        )

        return person
    }

    override fun updatePerson(person: PersonED): PersonED {
        val personRef = personCollection.document(person.email)

        await(
            personRef.update(personToMap(person))
                .addOnFailureListener {
                    throw RuntimeException(
                        "Failure updating person with email: ${person.email}",
                        it
                    )
                }
        )

        return person
    }

    override fun deletePersonByEmail(email: String) {
        val personRef = personCollection.document(email)

        await(
            personRef.delete().addOnFailureListener {
                throw RuntimeException(
                    "Failure deleting person with email: $email",
                    it
                )
            }
        )
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

    private fun mapToPerson(personMap: Map<String, Any?>): PersonED {
        return PersonED(
            personMap["email"] as String,
            personMap["photo"] as String?,
            personMap["name"] as String?,
            personMap["age"] as Long?,
            personMap["mark"] as Long?,
            personMap["tags"] as List<Long>?,
            personMap["metro"] as Metro?,
            personMap["money"] as Pair<Long, Long>?,
            personMap["rooms"] as List<String>?,
            personMap["description"] as String?
        )
    }
}

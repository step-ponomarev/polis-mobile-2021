package ru.mail.polis.dao

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import java.lang.RuntimeException

class PersonService(
    val db: FirebaseFirestore = Firebase.firestore,
    val personCollection: CollectionReference = db.collection(Collections.PERSON.collectionName)
) : IPersonService {
    override fun findByEmail(email: String): PersonED? {
        val personRef = personCollection.document(email)

        var person: PersonED? = null
        personRef.get()
            .addOnSuccessListener {
                person = it.toObject(PersonED::class.java)
            }
            .addOnFailureListener {
                throw RuntimeException(
                    "Failure fetching person",
                    it
                )
            }

        return person
    }

    override fun findAll(): List<PersonED> {
        var personList: List<PersonED> = emptyList()
        personCollection.get()
            .addOnSuccessListener {
                personList = it.toObjects()
            }
            .addOnFailureListener {
                throw RuntimeException(
                    "Failure fetching person list",
                    it
                )
            }

        return personList;
    }

    override fun addPerson(person: PersonED): PersonED {
        personCollection.document(person.email)
            .set(personToMap(person))
            .addOnFailureListener {
                throw RuntimeException(
                    "Failure adding person with email: ${person.email}",
                    it
                )
            };

        return person;
    }

    override fun updatePerson(person: PersonED): PersonED {
        val personRef = personCollection.document(person.email)
        personRef.update(personToMap(person))
            .addOnFailureListener {
                throw RuntimeException(
                    "Failure updating person with email: ${person.email}",
                    it
                )
            };

        return person;
    }

    override fun deletePersonByEmail(email: String) {
        val personRef = personCollection.document(email)

        personRef.delete().addOnFailureListener {
            throw RuntimeException(
                "Failure deleting person with email: ${email}",
                it
            )
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
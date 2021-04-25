package ru.mail.polis.db.firestore

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ApartmentRepository : IApartmentRepository {

    private val db = Firebase.firestore
    private val user = FirebaseAuth.getInstance().currentUser

    override fun save(apartment: HashMap<String, String>) {

        db.collection("users").document(user.email.toString())
            .set(apartment)
            .addOnSuccessListener { documentReference ->
                Log.d("firestore", "DocumentSnapshot added with ID:")
            }
            .addOnFailureListener { e ->
                Log.w("firestore", "Error adding document", e)
            }

    }

    override fun getApartments(email: String): MutableMap<String, Any>? {
        val document = db.collection("users").document(email)
            .get().addOnSuccessListener { document ->

                if (document != null) {
                    Log.d("Firestore", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }

        return document.result?.data
    }
}
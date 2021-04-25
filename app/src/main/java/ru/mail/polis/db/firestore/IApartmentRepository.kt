package ru.mail.polis.db.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface IApartmentRepository {
    fun save(apartment: HashMap<String, String>)
    fun getApartments(email: String): MutableMap<String, Any>?
}
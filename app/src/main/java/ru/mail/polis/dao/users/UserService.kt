package ru.mail.polis.dao.users

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mail.polis.dao.Collections
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserService : IUserService {

    private val db: FirebaseFirestore = Firebase.firestore
    private val userCollection: CollectionReference =
        db.collection(Collections.USER.collectionName)

    override suspend fun updateUserByEmail(email: String, user: UserED): UserED {
        val userRef = userCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            userRef.update(userToMap(user))
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException(
                            "Failure updating user with email: ${email}",
                            it
                        )
                    )
                }
                .addOnSuccessListener {
                    coroutine.resume(user)
                }
        }
    }

    override suspend fun findUserByEmail(email: String): UserED? {
        val userRef = userCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            userRef.get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObject(UserED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        RuntimeException("Filed fetching user with email: $email", it)
                    )
                }
        }
    }

    private fun userToMap(user: UserED): Map<String, Any?> {
        return mapOf(
            "email" to user.email,
            "name" to user.name,
            "surname" to user.surname,
            "age" to user.age,
            "phone" to user.phone,
            "photoUrl" to user.photo,
            "externalAccounts" to user.externalAccounts
        )
    }
}
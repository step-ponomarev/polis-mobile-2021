package ru.mail.polis.dao.users

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mail.polis.dao.Collections
import ru.mail.polis.dao.DaoException
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
                .addOnSuccessListener {
                    coroutine.resume(user)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure updating user with email: $email",
                            it
                        )
                    )
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
                        DaoException("Filed fetching user with email: $email", it)
                    )
                }
        }
    }

    override suspend fun findUsersByEmails(emails: Set<String>): List<UserED> {
        return suspendCancellableCoroutine { coroutine ->
            userCollection.whereIn("email", emails.toList())
                .get()
                .addOnSuccessListener {
                    coroutine.resume(it.toObjects(UserED::class.java))
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException("Filed fetching users by emails: $emails", it)
                    )
                }
        }
    }

    override suspend fun addUser(user: UserED): UserED {
        return suspendCancellableCoroutine { coroutine ->
            userCollection.document(user.email!!)
                .set(user)
                .addOnSuccessListener {
                    Log.i(
                        this::class.java.name,
                        "Successful adding user with email: ${user.email}"
                    )

                    coroutine.resume(user)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure adding user with email: ${user.email}",
                            it
                        )
                    )
                }
        }
    }

    override suspend fun isExist(email: String): Boolean {
        return suspendCancellableCoroutine { coroutine ->

            userCollection.document(email)
                .get()
                .addOnSuccessListener { document ->
                    Log.i(
                        this::class.java.name,
                        "Successful adding user with email: $email"
                    )
                    if (document.exists()) {
                        coroutine.resume(true)
                    } else {
                        coroutine.resume(false)
                    }
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure adding user with email: $email",
                            it
                        )
                    )
                }
        }
    }

    override suspend fun deleteUser(email: String) {
        val personRef = userCollection.document(email)

        return suspendCancellableCoroutine { coroutine ->
            personRef.delete()
                .addOnSuccessListener {
                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                    coroutine.resumeWithException(
                        DaoException(
                            "Failure deleting user with email: $email",
                            it
                        )
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
            "photo" to user.photo,
            "externalAccounts" to user.externalAccounts
        )
    }
}

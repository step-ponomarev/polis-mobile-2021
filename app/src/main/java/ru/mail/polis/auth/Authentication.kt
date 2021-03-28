package ru.mail.polis.auth

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ru.mail.polis.R

class Authentication {

    companion object {
        const val RC_SIGN_IN = 200
    }

    lateinit var firebaseAuth: FirebaseAuth

    lateinit var googleSignInClient: GoogleSignInClient

    fun createRequest(context: Context) {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    @SuppressLint("ShowToast")
    fun firebaseAuthWithGoogle(idToken: String, context: Context) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                } else {
                    Toast.makeText(context, "Failed to sign", Toast.LENGTH_SHORT)
                }
            }
    }
}

package ru.mail.polis.services

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ru.mail.polis.R

class GoogleAuthenticationService(private val context: Context) : AuthenticationService {

    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var firebaseAuth: FirebaseAuth
        private set

    override fun signIn() {
        val signInIntent = googleSignInClient.signInIntent

        val activity = context as Activity
        activity.startActivityForResult(signInIntent, AuthenticationService.RC_SIGN_IN)
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    fun createRequest() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        firebaseAuth = FirebaseAuth.getInstance()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }


    fun handleResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                } else {
                }
            }
    }
}
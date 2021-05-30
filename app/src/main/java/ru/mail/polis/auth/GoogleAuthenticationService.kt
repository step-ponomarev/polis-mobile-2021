package ru.mail.polis.auth

import android.accounts.AuthenticatorException
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthenticationService(private val singInClient: GoogleSignInClient) :
    AuthenticationService {

    companion object {
        private const val TAG = "AuthenticationService"
    }

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun getSignInIntent(): Intent {
        return singInClient.signInIntent
    }

    @Throws(AuthenticatorException::class)
    override fun handleResult(data: Intent?): String {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)

            return account.email!!
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)

            throw AuthenticatorException("Login Error", e)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
}

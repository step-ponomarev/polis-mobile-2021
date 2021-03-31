package ru.mail.polis.auth

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ru.mail.polis.LoginActivity

class GoogleAuthenticationService(private val singInClient: GoogleSignInClient) :
    AuthenticationService {

    companion object {
        private const val TAG = "Google Firebase auth"
    }

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun signIn() {
        val signInIntent = singInClient.signInIntent

        val activity: LoginActivity = singInClient.applicationContext as LoginActivity
        activity.startActivityForResult(signInIntent,
            AuthenticationService.SUCCESS_RESPONSE_CODE
        )
    }

    override fun handleResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
}

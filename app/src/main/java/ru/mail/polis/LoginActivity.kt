package ru.mail.polis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import ru.mail.polis.auth.Authentication

class LoginActivity : AppCompatActivity() {

    private val authentication = Authentication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authentication.createRequest(this)

        authentication.firebaseAuth = FirebaseAuth.getInstance()

        val button: Button = findViewById(R.id.gmail_login_button)
        button.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = authentication.firebaseAuth.currentUser
    }

    @SuppressLint("ShowToast")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Authentication.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                authentication.firebaseAuthWithGoogle(account.idToken!!, this)
            } catch (e: ApiException) {
                Toast.makeText(this, "Failed to sign", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun signIn() {
        val signInIntent = authentication.googleSignInClient.signInIntent

        startActivityForResult(signInIntent, Authentication.RC_SIGN_IN)
    }
}

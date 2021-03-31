package ru.mail.polis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ru.mail.polis.services.AuthenticationService
import ru.mail.polis.services.GoogleAuthenticationService

class LoginActivity : AppCompatActivity() {

    private val googleAuthentication = GoogleAuthenticationService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleAuthentication.createRequest()

        val button: Button = findViewById(R.id.gmail_login_button)
        button.setOnClickListener {
            googleAuthentication.signIn()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = googleAuthentication.firebaseAuth.currentUser
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AuthenticationService.RC_SIGN_IN) {
            googleAuthentication.handleResult(data)
        }
    }
}

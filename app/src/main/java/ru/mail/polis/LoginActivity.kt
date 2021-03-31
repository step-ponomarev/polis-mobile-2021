package ru.mail.polis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ru.mail.polis.auth.AuthenticationService
import ru.mail.polis.auth.GoogleSingInHelper

class LoginActivity : AppCompatActivity() {
    private val googleAuthentication: AuthenticationService =
        GoogleSingInHelper.getGoogleAuthService(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button: Button = findViewById(R.id.gmail_login_button)
        button.setOnClickListener {
            googleAuthentication.signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (AuthenticationService.isSuccessful(resultCode)) {
            googleAuthentication.handleResult(data)
        }
    }
}

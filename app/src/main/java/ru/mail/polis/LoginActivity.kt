package ru.mail.polis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin :Button = findViewById(R.id.gmail_login_button)
        btnLogin.setOnClickListener {
            when (it.id) {
                R.id.gmail_login_button -> {
                    val intent = Intent(this, ListOfPeopleActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }


}



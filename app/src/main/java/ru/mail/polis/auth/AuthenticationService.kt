package ru.mail.polis.auth

import android.content.Intent

interface AuthenticationService {
    fun getSignInIntent(): Intent
    fun handleResult(data: Intent?): Boolean
    fun signOut()
}
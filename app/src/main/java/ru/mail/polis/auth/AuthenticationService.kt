package ru.mail.polis.auth

import android.accounts.AuthenticatorException
import android.content.Intent

interface AuthenticationService {
    fun getSignInIntent(): Intent

    @Throws(AuthenticatorException::class)
    fun handleResult(data: Intent?): String
    fun signOut()
}

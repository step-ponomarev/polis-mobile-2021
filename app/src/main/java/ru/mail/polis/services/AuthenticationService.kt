package ru.mail.polis.services

interface AuthenticationService {

    companion object {
        const val RC_SIGN_IN = 200
    }

    fun signIn()

    fun signOut()
}
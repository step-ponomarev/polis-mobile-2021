package ru.mail.polis.services

import android.content.Intent

interface AuthenticationService {
    companion object {
        const val SUCCESS_RESPONSE_CODE = 200

        fun isSuccessful(resultCode: Int): Boolean {
            return resultCode == SUCCESS_RESPONSE_CODE
        }
    }

    fun signIn()
    fun handleResult(data: Intent?)
    fun signOut()
}

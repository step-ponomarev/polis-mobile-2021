package ru.mail.polis.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import ru.mail.polis.R

class GoogleSingInUtils {
    companion object {
        fun getGoogleAuthService(applicationContext: Context): GoogleAuthenticationService {
            return GoogleAuthenticationService(
                GoogleSignIn.getClient(
                    applicationContext,
                    getSingInOptions(
                        applicationContext.getString(R.string.default_web_client_id)
                    )
                )
            )
        }

        private fun getSingInOptions(tokenId: String): GoogleSignInOptions {
            return GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(tokenId)
                .requestEmail()
                .build()
        }
    }
}

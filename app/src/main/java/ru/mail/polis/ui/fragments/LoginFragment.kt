package ru.mail.polis.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ru.mail.polis.R
import ru.mail.polis.auth.AuthenticationService
import ru.mail.polis.auth.GoogleSingInUtils

class LoginFragment : Fragment() {
    companion object {
        const val NAME = "LoginFragment"
    }

    private val loginForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), this::handleResult)

    private lateinit var googleAuthentication: AuthenticationService;
    private lateinit var singInButton: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        googleAuthentication = GoogleSingInUtils.getGoogleAuthService(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        singInButton = view.findViewById(R.id.gmail_login_button)
        singInButton.setOnClickListener(this::onClickSingIn);
    }

    private fun onClickSingIn(view: View) {
        loginForResult.launch(googleAuthentication.getSignInIntent())
    }

    private fun handleResult(result: ActivityResult) {
        googleAuthentication.handleResult(result.data)
    }
}
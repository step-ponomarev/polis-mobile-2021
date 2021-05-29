package ru.mail.polis.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.R
import ru.mail.polis.auth.AuthenticationService
import ru.mail.polis.auth.GoogleSingInUtils
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.exception.DaoException
import ru.mail.polis.exception.NotificationKeeperException

class LoginFragment : Fragment() {
    companion object {
        const val NAME = "LoginFragment"
    }

    private val loginForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        this::handleResult
    )

    private lateinit var googleAuthentication: AuthenticationService
    private lateinit var singInButton: View

    private val userService: IUserService = UserService()

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
        singInButton.setOnClickListener(this::onClickSignIn)
    }

    private fun onClickSignIn(view: View) {
        loginForResult.launch(googleAuthentication.getSignInIntent())
    }

    private fun handleResult(result: ActivityResult) {
        try {
            val email = googleAuthentication.handleResult(result.data)
            saveEmail(email)

            GlobalScope.launch(Dispatchers.Main) {
                if (checkIfUserExist(email)) {
                    findNavController().navigate(R.id.nav_graph__self_definition_fragment)
                } else {
                    findNavController().navigate(R.id.nav_graph__filling_profile_info)
                }
            }
        } catch (e: NotificationKeeperException) {
            getToast(getString(e.getResourceStringCode())).show()
        } catch (e: Exception) {
            Log.e("Auth error", e.message, e)
        }
    }

    private fun saveEmail(email: String) {
        activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
            ?.edit()
            ?.putString(getString(R.string.preference_email_key), email)
            ?.apply()
    }

    @Throws(NotificationKeeperException::class)
    private suspend fun checkIfUserExist(email: String): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                userService.isExist(email)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed checking user existence by email: $email",
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    private fun getToast(text: String): Toast {
        return Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        )
    }
}

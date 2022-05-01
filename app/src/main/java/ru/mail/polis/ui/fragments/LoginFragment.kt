package ru.mail.polis.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationCenter
import ru.mail.polis.notification.NotificationKeeperException
import ru.mail.polis.utils.NetworkUtils
import ru.mail.polis.utils.StorageUtils


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
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val personService: IPersonService = PersonService.getInstance()

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

        if (!NetworkUtils.isConnected(requireContext())) {
            NotificationCenter.showDefaultToast(
                requireContext(),
                getString(R.string.error_dao)
            )

            return
        }

        loginForResult.launch(googleAuthentication.getSignInIntent())
    }

    private fun handleResult(result: ActivityResult) {
        try {
            val email = googleAuthentication.handleResult(result.data)
            StorageUtils.setValue(requireContext(), StorageUtils.StorageKey.EMAIL, email)

            GlobalScope.launch(Dispatchers.Main) {
                if (checkIfUserExist(email)) {
                    if (checkAdvertExist(email) || checkAdvertExist(email)) {
                        findNavController().navigate(R.id.nav_graph__list_of_people)
                    } else {
                        findNavController().navigate(R.id.nav_graph__self_definition_fragment)
                    }
                } else {
                    findNavController().navigate(R.id.nav_graph__filling_profile_info)
                }
            }
        } catch (e: NotificationKeeperException) {
            NotificationCenter.showDefaultToast(
                requireContext(),
                getString(e.getResourceStringCode())
            )
        } catch (e: Exception) {
            NotificationCenter.showDefaultToast(
                requireContext(),
                getString(R.string.error_dao)
            )
            Log.e("Auth error", e.message, e)
        }
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

    private suspend fun checkApartmentExist(email: String): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                apartmentService.isExist(email)
            }
        }
        catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed checking user existence by email: $email",
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    private suspend fun checkAdvertExist(email: String): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                personService.isExist(email)
            }
        }
        catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed checking user existence by email: $email",
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }
}

package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.LoginFragment

object LoginScreen : KScreen<LoginScreen>() {
    override val layoutId: Int? = R.layout.fragment_login
    override val viewClass: Class<*> = LoginFragment::class.java

    private val loginButton = KButton { withId(R.id.gmail_login_button) }

    fun login() {
        loginButton {
            isVisible()
            click()
        }
    }

}
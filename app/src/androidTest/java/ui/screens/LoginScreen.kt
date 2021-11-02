package ui.screens

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.LoginFragment

object LoginScreen: KScreen<LoginScreen>() {
    override val layoutId: Int? = R.layout.fragment_login
    override val viewClass: Class<*> = LoginFragment::class.java

    val loginButton = KButton { withId(R.id.gmail_login_button) }
}
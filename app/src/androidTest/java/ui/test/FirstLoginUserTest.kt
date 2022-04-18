package ui.test

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import ru.mail.polis.MainActivity
import ru.mail.polis.R
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserService
import ui.screens.LoginScreen

class FirstLoginUserTest : TestCase() {
    @get:Rule
    private val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val userService:IUserService = UserService()
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun loginWithDisabledInternetConnection() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            };

        }.after {

        }.run {

            LoginScreen {
                login()
            }
        }
    }
}
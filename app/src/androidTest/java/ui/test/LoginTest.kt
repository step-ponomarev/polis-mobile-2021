package ui.test

import android.content.Context
import android.net.wifi.WifiManager
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import ru.mail.polis.MainActivity
import ru.mail.polis.R
import ui.screens.LoginScreen


class LoginTest : TestCase() {

    @get:Rule
    private val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val wifiManager: WifiManager =
        appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @Test
    fun loginWithDisabledInternetConnection() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            };

            device.network.toggleWiFi(false)
        }.after {
            device.network.toggleWiFi(true)

            assertTrue(wifiManager.isWifiEnabled)
        }.run {
            assertFalse(wifiManager.isWifiEnabled)

            LoginScreen {
                login()
                val messageExpected = appContext.getString(R.string.error_dao)
                onView(withText(messageExpected))
                    .inRoot(withDecorView(not(decorView)))// Here we use decorView
                    .check(matches(isDisplayed()));
            }
        }
    }
}
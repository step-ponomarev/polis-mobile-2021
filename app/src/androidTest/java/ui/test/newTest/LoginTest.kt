package ui.test.newTest

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
import ui.data.getTestApartment
import ui.data.getTestPerson
import ui.data.getTestUser
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SelfDefinitionScreen
import ui.utils.ServiceUtils

class LoginTest : TestCase() {

    //ДБ паблик
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val person = getTestPerson()
    private val apartment = getTestApartment()
    private val user = getTestUser()

    private val wifiManager: WifiManager =
        appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @Test
    fun loginWithDisabledInternetConnection() {
        before {
            val scenario = activityScenarioRule.scenario
            activityScenarioRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

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

    //TODO исправить в логин фрагменте, чтобы чекать, а то там мелькает другой экран
    @Test
    fun loginWithExistingAccountAndApartment() {
        before {
            val scenario = activityScenarioRule.scenario
            activityScenarioRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            ServiceUtils.createUser(user)
            ServiceUtils.createApartment(apartment)
        }.after {
            ServiceUtils.deleteUser(user)
            ServiceUtils.deleteApartment(apartment)
        }.run {
            LoginScreen {
                login()
            }

            ListOfPeopleScreen {
                isLoaded()
            }
        }
    }

    @Test
    fun loginWithExistingAccountAndAdvert() {
        before {
            val scenario = activityScenarioRule.scenario
            activityScenarioRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            ServiceUtils.createUser(user)
            ServiceUtils.createAdvert(person)
        }.after {
            ServiceUtils.deleteUser(user)
            ServiceUtils.deletePerson(person)
        }.run {
            LoginScreen {
                login()
            }

            ListOfPeopleScreen {
                isLoaded()
            }
        }
    }

    @Test
    fun loginWithExistingAccount() {
        before {
            val scenario = activityScenarioRule.scenario
            activityScenarioRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            ServiceUtils.createUser(user)
        }.after {
            ServiceUtils.deleteUser(user)
        }.run {
            LoginScreen {
                login()
            }

            SelfDefinitionScreen {
                isLoaded()
            }
        }
    }
}
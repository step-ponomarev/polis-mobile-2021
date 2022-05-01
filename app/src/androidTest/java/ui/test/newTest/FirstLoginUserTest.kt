package ui.test.newTest

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import ru.mail.polis.MainActivity
import ru.mail.polis.R
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ui.data.getTestUser
import ui.screens.FirstCreationFragmentScreen
import ui.screens.LoginScreen
import ui.screens.SelfDefinitionScreen

class FirstLoginUserTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val userService: IUserService = UserService()
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val testUser = getTestUser()


    //TODO ДОДЕЛАТЬ ТУТ С ТЕЛЕФОНОМ ПРОБЛЕМУ
    @Test
    @Ignore
    fun firstCreationWithAllFieldsAreFilled() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            deleteUser(testUser)

        }.after {
            deleteUser(testUser)
        }.run {

            step("Login and navigate to ListOfPeople") {
                LoginScreen {
                    login()
                }
            }

            step("Fill info") {
                FirstCreationFragmentScreen {
                    fillInfo(testUser)
                    clickContinueButton()
                }
            }

            step("Navigate to SelfDefinitonScreen") {
                SelfDefinitionScreen {
                    isLoaded()
                }
            }
        }
    }

    @Test
    fun firstCreationWithToast() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            deleteUser(testUser)
        }.after {
            deleteUser(testUser)
        }.run {

            step("Login and navigate to ListOfPeople") {
                LoginScreen {
                    login()
                }
            }

            step("Check toast when not all fields are filled") {
                FirstCreationFragmentScreen {

                    fillAllExceptName(testUser)
                    clickContinueButton()
                    checkToast()

                    fillAllExceptSurname(testUser)
                    clickContinueButton()
                    checkToast()

                    fillAllExceptPhone(testUser)
                    clickContinueButton()
                    checkToast()

                    fillAllExceptAge(testUser)
                    clickContinueButton()
                    checkToast()
                }
            }
        }
    }

    private fun deleteUser(userED: UserED) {
        runBlocking {
            userService.deleteUser(userED.email!!)
        }
    }

    private fun checkToast() {
        val messageExpected = appContext.getString(R.string.toast_fill_all_information_about_user)
        onView(ViewMatchers.withText(messageExpected))
            .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))// Here we use decorView
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
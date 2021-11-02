package ui

import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.mail.polis.MainActivity
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SelfDefinitionScreen

class FirstUiTest : TestCase() {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)


    @Test
    fun test() {
//        onView(withId(R.layout.fragment_login)).perform(click())

        run {
            step("Try to start this test") {
                activityTestRule.launchActivity(null)
                LoginScreen {
                    loginButton {
                        isVisible()
                        click()
                    }
                    SelfDefinitionScreen {
                        findAppartmentButton {
                            isVisible()
                        }
                        rentApartmentButton {
                            isVisible()
                        }
                    }
                    ListOfPeopleScreen
                }
            }

            step("Another Screen") {

            }
        }
    }
}
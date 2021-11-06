package ui

import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.mail.polis.MainActivity
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SelfDefinitionScreen
import ui.screens.SettingsScreen

class FirstUiTest : TestCase() {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)


    @Test
    fun editInfoInSettings() {
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
                    ListOfPeopleScreen {
                        settingsButton {
                            isVisible()
                            click()
                        }
                    }
                    SettingsScreen {
                        editButton {
                            isVisible()
                            isClickable()
                        }
                    }
                }
            }

            step("Another Screen") {

            }
        }
    }
}

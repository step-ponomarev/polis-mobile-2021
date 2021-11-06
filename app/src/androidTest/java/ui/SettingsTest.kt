package ui

import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.mail.polis.MainActivity
import ui.data.User
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SettingsScreen

class SettingsTest : TestCase() {
    //TODO чекнуть scenario и мб заюзать их
    //TODO мб убрать тест данные в спец блок init
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val defaultUserInfo: User = User(
        name = "Иван",
        surname = "Иванов",
        phone = "89025563557",
        age = 38
    )

    private val testUser: User = User(
        name = "Артем",
        surname = "Петров",
        phone = "89082122231",
        age = 45
    )

    @Test
    fun test() {
        before {
            activityTestRule.launchActivity(null)

        }.after {
            SettingsScreen {
                changeUserInfo(defaultUserInfo)
            }
        }.run {
            step("Login and navigate to ListOfPeople") {
                LoginScreen {
                    loginButton {
                        isVisible()
                        click()
                    }
                }
            }

            step("Navigate to settings") {
                ListOfPeopleScreen {
                    navigateToSettings()
                }
            }

            step("Check current user, change user info and navigate to ListOfPeople") {
                SettingsScreen {
                    checkUserInfo(defaultUserInfo)
                    changeUserInfo(testUser)

                    navigateToListOfPeople()
                }
            }

            step("Navigate to Settings") {
                ListOfPeopleScreen {
                    navigateToSettings()
                }
            }

            step("Check that user info was changed") {
                SettingsScreen {
                    checkUserInfo(testUser)
                }
            }

        }
    }
}

package ui.test.newTest

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
import ui.data.getTestApartment
import ui.data.getTestPerson
import ui.data.getTestUser
import ui.data.getUpdatedTestUser
import ui.screens.AdvertDialogScreen
import ui.screens.ApartmentDialog
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SettingsScreen
import ui.utils.ServiceUtils

class SettingsTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var decorView: View
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val oldUserInfo = getTestUser()
    private val updatedTestUser = getUpdatedTestUser()
    private val apartment = getTestApartment()
    private val person = getTestPerson()

    @Test
    fun changeUserInfoTest() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            ServiceUtils.createUser(oldUserInfo)
            ServiceUtils.createApartment(apartment)
        }.after {

            ServiceUtils.deleteApartment(apartment)
            ServiceUtils.deleteUser(updatedTestUser)
        }.run {
            step("Login and navigate to ListOfPeople") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to settings") {
                ListOfPeopleScreen {
                    navigateToSettings()
                }
            }

            step("Check current user, change user info and navigate to ListOfPeople") {
                SettingsScreen {
                    isLoaded()

                    checkUserInfo(oldUserInfo)
                    changeUserInfo(updatedTestUser)

                    checkSavedToast()

                    navigateToListOfPeople()
                    println("after nagitate to list of people")
                }
            }

            step("Navigate to Settings") {
                ListOfPeopleScreen {
                    navigateToSettings()
                    println("after nagitate to settings")
                }
            }

            step("Check that user info was changed") {
                SettingsScreen {
                    println("in check last settings")
                    checkUserInfo(updatedTestUser)
                }
            }
        }
    }

    @Test
    fun changeToNotValidAndCheckForToast() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            ServiceUtils.createUser(oldUserInfo)
            ServiceUtils.createApartment(apartment)
        }.after {

            ServiceUtils.deleteApartment(apartment)
            ServiceUtils.deleteUser(oldUserInfo)
        }.run {
            step("Login and navigate to ListOfPeople") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to settings") {
                ListOfPeopleScreen {
                    navigateToSettings()
                }
            }

            step("Check toast when try to edit with empty fields") {
                SettingsScreen {
                    isLoaded()

                    fillAllExceptName(updatedTestUser)
                    clickEditButton()
                    checkFieldsToast()

                    fillAllExceptSurname(updatedTestUser)
                    clickEditButton()
                    checkFieldsToast()

                    fillAllExceptPhone(updatedTestUser)
                    clickEditButton()
                    checkFieldsToast()

                    fillAllExceptAge(updatedTestUser)
                    clickEditButton()
                    checkFieldsToast()
                }
            }
        }
    }

    @Test
    fun checkDialogWhenApartmentNotExist() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            ServiceUtils.createUser(oldUserInfo)
            ServiceUtils.createAdvert(person)
        }.after {

            ServiceUtils.deletePerson(person)
            ServiceUtils.deleteUser(oldUserInfo)
        }.run {
            step("Login and navigate to ListOfPeople") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to settings") {
                ListOfPeopleScreen {
                    navigateToSettings()
                }
            }

            step("Check dialog that apartment does not exist") {
                SettingsScreen {
                    isLoaded()

                    clickApartmentButton()
                }
            }

            step("Open dialog and check") {
                ApartmentDialog {
                    isLoaded()
                }
            }
        }
    }

    @Test
    fun checkDialogWhenPersonNotExist() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            ServiceUtils.createUser(oldUserInfo)
            ServiceUtils.createApartment(apartment)
        }.after {

            ServiceUtils.deleteApartment(apartment)
            ServiceUtils.deleteUser(oldUserInfo)
        }.run {
            step("Login and navigate to ListOfPeople") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to settings") {
                ListOfPeopleScreen {
                    navigateToSettings()
                }
            }

            step("Check dialog that advert does not exist") {
                SettingsScreen {
                    isLoaded()

                    clickAdvertButton()
                }
            }

            step("Open dialog and check") {
                AdvertDialogScreen {
                    isLoaded()
                }
            }
        }
    }

    private fun checkFieldsToast() {
        val messageExpected =
            appContext.getString(R.string.toast_fill_all_information_about_user)

        checkToast(messageExpected)
    }

    private fun checkSavedToast() {
        val messageExpected = appContext.getString(R.string.toast_changes_are_saved)

        checkToast(messageExpected)
    }

    private fun checkToast(message: String) {

        Espresso.onView(ViewMatchers.withText(message))
            .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))// Here we use decorView
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}

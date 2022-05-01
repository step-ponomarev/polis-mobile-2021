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
import ui.data.getTestUser
import ui.data.getUpdatedTestApartment
import ui.screens.ApartmentEditingScreen
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SettingsScreen
import ui.utils.ServiceUtils

class ApartmentEditingTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val apartment = getTestApartment()
    private val updatedApartment = getUpdatedTestApartment()
    private val testUser = getTestUser()

    @Test
    fun apartmentEditingWithAllFieldsAreFilled() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)
            ServiceUtils.createApartment(apartment)
        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deleteApartment(updatedApartment)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to main screen") {
                ListOfPeopleScreen {
                    isLoaded()

                    navigateToSettings()
                }
            }

            step("In Setting choose change apartment") {
                SettingsScreen {
                    isLoaded()

                    clickApartmentButton()
                }
            }

            step("Change apartment") {
                ApartmentEditingScreen {
                    isLoaded()

                    changeApartmentInfo(updatedApartment)
                    checkSavedToast()

                    device.exploit.pressBack()
                }
            }

            step("Back to settings") {
                SettingsScreen {
                    isLoaded()

                    clickApartmentButton()
                }
            }

            step("Return to editing apartment and check that saved") {
                ApartmentEditingScreen {
                    isLoaded()

                    checkApartmentInfo(updatedApartment)
                }
            }
        }
    }

    @Test
    fun checkToastWhenNotAllFieldsFilled() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)
            ServiceUtils.createApartment(apartment)
        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deleteApartment(apartment)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to main screen") {
                ListOfPeopleScreen {
                    isLoaded()

                    navigateToSettings()
                }
            }

            step("In Setting choose change apartment") {
                SettingsScreen {
                    isLoaded()

                    clickApartmentButton()
                }
            }

            step("Change apartment with not all field are filled") {
                ApartmentEditingScreen {
                    isLoaded()

                    //Chip group не трогаю

                    //Чтобы подождать загрузку
//                    Thread.sleep(3000)

                    fillAllExceptCost(apartment)
                    clickEditButton()
                    checkFieldsToast()

                    fillAllExceptMetres(apartment)
                    clickEditButton()
                    checkFieldsToast()
                }
            }
        }
    }

    private fun checkFieldsToast() {
        val messageExpected =
            appContext.getString(R.string.toast_fill_all_information_about_apartment)

        checkToast(messageExpected)
    }

    private fun checkSavedToast() {
        val messageExpected = appContext.getString(R.string.toast_user_changed_apartment_info)

        checkToast(messageExpected)
    }

    private fun checkToast(message: String) {

        Espresso.onView(ViewMatchers.withText(message))
            .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))// Here we use decorView
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
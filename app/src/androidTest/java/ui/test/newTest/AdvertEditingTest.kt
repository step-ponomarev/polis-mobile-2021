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
import ui.data.getTestPerson
import ui.data.getTestUser
import ui.data.getUpdatedTestPerson
import ui.screens.AdvertEditingScreen
import ui.screens.ApartmentEditingScreen
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SettingsScreen
import ui.utils.ServiceUtils

class AdvertEditingTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val person = getTestPerson()
    private val updatedPerson = getUpdatedTestPerson()
    private val testUser = getTestUser()

    @Test
    fun advertEditingWithAllFieldsAreFilled() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)
            ServiceUtils.createAdvert(person)
        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deletePerson(updatedPerson)
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

            step("In Setting choose change advert") {
                SettingsScreen {
                    isLoaded()

                    clickAdvertButton()
                }
            }

            step("Change advert") {
                AdvertEditingScreen {
                    isLoaded()

                    changeAdvertInfo(updatedPerson)
                    checkSavedToast()

                    device.exploit.pressBack()
                }
            }

            step("Back to settings") {
                SettingsScreen {
                    isLoaded()

                    clickAdvertButton()
                }
            }

            step("Return to editing apartment and check that saved") {
                AdvertEditingScreen {
                    isLoaded()

                    checkAdvertInfo(updatedPerson)
                }
            }
        }
    }

//    @Test
//    fun checkToastWhenNotAllFieldsFilled() {
//        before {
//            activityTestRule.scenario.onActivity {
//                decorView = it.window.decorView;
//            }
//
//            //должен быть юзер, значить надо его добавить, после удалить
//            ServiceUtils.createUser(testUser)
//            ServiceUtils.createApartment(apartment)
//        }.after {
//            ServiceUtils.deleteUser(testUser)
//            ServiceUtils.deleteApartment(apartment)
//        }.run {
//
//            step("Login") {
//                LoginScreen {
//                    login()
//                }
//            }
//
//            step("Navigate to main screen") {
//                ListOfPeopleScreen {
//                    isLoaded()
//
//                    navigateToSettings()
//                }
//            }
//
//            step("In Setting choose change apartment") {
//                SettingsScreen {
//                    isLoaded()
//
//                    clickApartmentButton()
//                }
//            }
//
//            step("Change apartment with not all field are filled") {
//                ApartmentEditingScreen {
//                    isLoaded()
//
//                    //Chip group не трогаю
//
//                    //Чтобы подождать загрузку
////                    Thread.sleep(3000)
//
//                    fillAllExceptCost(apartment)
//                    clickEditButton()
//                    checkFieldsToast()
//
//                    fillAllExceptMetres(apartment)
//                    clickEditButton()
//                    checkFieldsToast()
//                }
//            }
//        }
//    }

    private fun checkFieldsToast() {
        val messageExpected =
            appContext.getString(R.string.toast_fill_all_information_about_apartment)

        checkToast(messageExpected)
    }

    private fun checkSavedToast() {
        val messageExpected = appContext.getString(R.string.toasts_user_changed_advert_info)

        checkToast(messageExpected)
    }

    private fun checkToast(message: String) {

        Espresso.onView(ViewMatchers.withText(message))
            .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))// Here we use decorView
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
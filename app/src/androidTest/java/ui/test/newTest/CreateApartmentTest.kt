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
import ui.screens.AddApartmentScreen
import ui.screens.ApartmentDialog
import ui.screens.ApartmentEditingScreen
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SelfDefinitionScreen
import ui.screens.SettingsScreen
import ui.utils.ServiceUtils

class CreateApartmentTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val apartment = getTestApartment()
    private val testUser = getTestUser()
    private val person = getTestPerson()

    @Test
    fun firstCreationApartmentWithAllFieldsAreFilled() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)

        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deleteApartment(apartment)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to screen with two buttons and choose add apartment") {
                SelfDefinitionScreen {
                    isLoaded()
                    clickAddApartment()
                }
            }

            step("Navigate to add apartment") {
                AddApartmentScreen {
                    isLoaded()

                    fillApartmentInfo(apartment)
                    clickAddButton()
                }
            }

            step("Navigate to people list") {
                ListOfPeopleScreen {
                    isLoaded()
                }
            }
        }
    }

    @Test
    fun firstCreationApartmentWithNotAllFieldFilled() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)

        }.after {
            ServiceUtils.deleteUser(testUser)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to screen with two buttons and choose add apartment") {
                SelfDefinitionScreen {
                    isLoaded()
                    clickAddApartment()
                }
            }

            step("Navigate to add apartment and check that field are not filled") {
                AddApartmentScreen {
                    isLoaded()

                    //ДБ первым т.к нельзя анчекнуть чип
                    fillAllExceptNumberRooms(apartment)
                    clickAddButton()
                    checkToast()

                    fillAllExceptCost(apartment)
                    clickAddButton()
                    checkToast()

                    fillAllExceptMetres(apartment)
                    clickAddButton()
                    checkToast()
                }
            }
        }
    }

    @Test
    fun createApartmentFromSettingsWhenAdvertIsExist() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)
            ServiceUtils.createAdvert(person)

        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deletePerson(person)
            ServiceUtils.deleteApartment(apartment)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("To main screen") {
                ListOfPeopleScreen {
                    isLoaded()

                    navigateToSettings()
                }
            }

            step("Click apartment button") {
                SettingsScreen {
                    isLoaded()

                    clickApartmentButton()
                }
            }

            step("Dialog to create must appear and choose create") {
                ApartmentDialog {
                    isLoaded()

                    clickAdd()
                }
            }

            step("Create apartment") {
                AddApartmentScreen {
                    isLoaded()

                    fillApartmentInfo(apartment)
                    clickAddButton()
                }
            }

            step("Navigate to main screen") {
                ListOfPeopleScreen {
                    isLoaded()

                    navigateToSettings()
                }
            }

            step("Click add apartment") {
                SettingsScreen {
                    isLoaded()

                    clickApartmentButton()
                }
            }

            step("Check that was added") {
                ApartmentEditingScreen {
                    isLoaded()

                    checkApartmentInfo(apartment)
                }
            }
        }
    }

    private fun checkToast() {
        val messageExpected =
            appContext.getString(R.string.toast_fill_all_information_about_apartment)
        Espresso.onView(ViewMatchers.withText(messageExpected))
            .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))// Here we use decorView
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
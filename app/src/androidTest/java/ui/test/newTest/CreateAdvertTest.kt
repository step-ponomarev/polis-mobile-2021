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
import ui.screens.AddAdvertScreen
import ui.screens.AdvertDialogScreen
import ui.screens.AdvertEditingScreen
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SelfDefinitionScreen
import ui.screens.SettingsScreen
import ui.utils.ServiceUtils

class CreateAdvertTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val person = getTestPerson()
    private val testUser = getTestUser()
    private val apartment = getTestApartment()

    @Test
    fun firstCreationAdvertWithAllFieldsAreFilled() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)

        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deletePerson(person)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to screen with two buttons and choose add advert") {
                SelfDefinitionScreen {
                    isLoaded()
                    clickAddAdvert()
                }
            }

            step("Navigate to add advert") {
                AddAdvertScreen {
                    isLoaded()

                    fillAllFields(person)
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
    fun firstCreationAdvertWithNotAllFieldFilled() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)

        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deletePerson(person)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to screen with two buttons and choose add apartment") {
                SelfDefinitionScreen {
                    isLoaded()
                    clickAddAdvert()
                }
            }

            step("Navigate to add advert and check that field are not filled") {
                AddAdvertScreen {
                    isLoaded()

                    //ДБ первым т.к нельзя анчекнуть чип
                    fillAllExceptNumberRooms(person)
                    clickAddButton()
                    checkFieldsToast()

                    fillAllExceptCost(person)
                    clickAddButton()
                    checkFieldsToast()

                    fillAllExceptMetres(person)
                    clickAddButton()
                    checkFieldsToast()
                }
            }
        }
    }

    @Test
    fun checkNotificationWhenIncorrectRange() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)

        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deletePerson(person)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to screen with two buttons and choose add apartment") {
                SelfDefinitionScreen {
                    isLoaded()
                    clickAddAdvert()
                }
            }

            step("Navigate to add advert and check that field are not filled") {
                AddAdvertScreen {
                    isLoaded()

                    val advertWithIncorrectCostRange = getTestPerson()
                    advertWithIncorrectCostRange.moneyFrom = 15000
                    advertWithIncorrectCostRange.moneyTo = 10000

                    val advertWithIncorrectSquareRange = getTestPerson()
                    advertWithIncorrectSquareRange.metresFrom = 15
                    advertWithIncorrectSquareRange.metresTo = 10

                    fillAllFields(advertWithIncorrectCostRange)
                    clickAddButton()
                    checkRangeCostToast()
//Слипы,чтобы прошлый тоаст пропал
                    Thread.sleep(3000)

                    fillAllFields(advertWithIncorrectSquareRange)
                    clickAddButton()
                    checkRangeSquareToast()

                    Thread.sleep(3000)
                }
            }
        }
    }

    @Test
    fun createAdvertFromSettingsWhenApartmentIsExist() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)
            ServiceUtils.createApartment(apartment)

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

                    clickAdvertButton()
                }
            }

            step("Dialog to create must appear and choose create") {
                AdvertDialogScreen {
                    isLoaded()

                    clickAdd()
                }
            }

            step("Create apartment") {
                AddAdvertScreen {
                    isLoaded()

                    fillAllFields(person)
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

                    clickAdvertButton()
                }
            }

            step("Check that was added") {
                AdvertEditingScreen {
                    isLoaded()

                    checkAdvertInfo(person)
                }
            }
        }
    }

    private fun checkFieldsToast() {
        val messageExpected =
            appContext.getString(R.string.toast_fill_all_advert_info)

        checkToast(messageExpected)
    }

    private fun checkRangeCostToast() {
        val messageExpected = "Первое число диапазона цен должно быть меньше второго"

        checkToast(messageExpected)
    }

    private fun checkRangeSquareToast() {
        val messageExpected = "Первое число диапазона метров должно быть меньше второго"

        checkToast(messageExpected)
    }

    private fun checkToast(message: String) {

        Espresso.onView(ViewMatchers.withText(message))
            .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))// Here we use decorView
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
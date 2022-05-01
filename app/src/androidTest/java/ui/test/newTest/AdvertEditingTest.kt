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

    @Test
    fun checkToastWhenNotAllFieldsFilled() {
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

            step("Change advert and not fill all fields") {
                AdvertEditingScreen {
                    isLoaded()


                    fillAllExceptCost(updatedPerson)
                    clickEditButton()
                    checkFieldsToast()

                    fillAllExceptMetres(updatedPerson)
                    clickEditButton()
                    checkFieldsToast()
                }
            }
        }
    }

    @Test
    fun checkToastWhenIncorrectRanges() {
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

            step("Change advert and not correct ranges") {
                AdvertEditingScreen {
                    isLoaded()


                    val advertWithIncorrectCostRange = getUpdatedTestPerson()
                    advertWithIncorrectCostRange.moneyFrom = 20000
                    advertWithIncorrectCostRange.moneyTo = 10000


                    val advertWithIncorrectSquareRange = getUpdatedTestPerson()
                    advertWithIncorrectSquareRange.metresFrom = 20
                    advertWithIncorrectSquareRange.metresTo = 10

                    changeAdvertInfo(advertWithIncorrectCostRange)
                    checkRangeCostToast()
                    Thread.sleep(3000)

//Слипы чтобы прошлый тоаст пропал
                    changeAdvertInfo(advertWithIncorrectSquareRange)
                    checkRangeSquareToast()
                    Thread.sleep(3000)

                }
            }
        }
    }

    private fun checkFieldsToast() {
        val messageExpected =
            appContext.getString(R.string.toast_fill_all_advert_info)

        checkToast(messageExpected)
    }

    private fun checkSavedToast() {
        val messageExpected = appContext.getString(R.string.toasts_user_changed_advert_info)

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
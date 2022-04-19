package ui.test

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.mail.polis.MainActivity
import ui.data.getTestApartment
import ui.data.getTestUser
import ui.screens.AddApartmentScreen
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SelfDefinitionScreen
import ui.utils.ServiceUtils

class CreateApartmentTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val apartment = getTestApartment()
    private val testUser = getTestUser()

    @Test
    fun firstCreationWithAllFieldsAreFilled() {
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
}
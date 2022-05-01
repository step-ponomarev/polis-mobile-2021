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
import ru.mail.polis.dao.users.UserED
import ui.data.getPersonToOfferApartment
import ui.data.getTestApartment
import ui.data.getTestPerson
import ui.data.getTestUser
import ui.data.getUserToOfferApartment
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.PersonAdvertScreen
import ui.utils.ServiceUtils

class OfferApartmentTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var decorView: View
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val apartment = getTestApartment()
    private val testUser = getTestUser()
    private val person = getTestPerson()

    private val userToOfferApartment = getUserToOfferApartment()
    private val personToOfferApartment = getPersonToOfferApartment()


    @Test
    fun offerApartmentTest() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(userToOfferApartment)
            ServiceUtils.createAdvert(personToOfferApartment)

            ServiceUtils.createUser(testUser)
            ServiceUtils.createApartment(apartment)

        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deleteUser(userToOfferApartment)
            ServiceUtils.deletePerson(personToOfferApartment)
            ServiceUtils.deleteApartment(apartment)
            ServiceUtils.deletePropose(testUser, userToOfferApartment)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to people list") {
                ListOfPeopleScreen {
                    isLoaded()

                    offerApartment(apartment, personToOfferApartment, userToOfferApartment)
                }
            }

            step("Navigate to advert and offer apartment") {
                PersonAdvertScreen {
                    isLoaded()
                    checkPerson(userToOfferApartment)

                    offerApartment()
                    checkOfferedApartmentToast(userToOfferApartment)
                }
            }
        }
    }

    @Test
    fun testThatYouCanOfferYourself() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)
            ServiceUtils.createAdvert(person)
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

            step("Navigate to people list") {
                ListOfPeopleScreen {
                    isLoaded()

                    offerApartment(apartment, person, testUser)
                }
            }

            step("Navigate to advert and offer apartment") {
                PersonAdvertScreen {
                    isLoaded()
                    checkPerson(testUser)

                    offerApartment()
                    checkThatYouCanOfferedYourselfToast()
                }
            }
        }
    }

    @Test
    fun testThatYouCantOfferApartmentIfDontHaveIT() {
        before {
            activityTestRule.scenario.onActivity {
                decorView = it.window.decorView;
            }

            //должен быть юзер, значить надо его добавить, после удалить
            ServiceUtils.createUser(testUser)
            ServiceUtils.createAdvert(person)

            ServiceUtils.createUser(userToOfferApartment)
            ServiceUtils.createAdvert(personToOfferApartment)

        }.after {
            ServiceUtils.deleteUser(testUser)
            ServiceUtils.deletePerson(person)

            ServiceUtils.deleteUser(userToOfferApartment)
            ServiceUtils.deletePerson(personToOfferApartment)
        }.run {

            step("Login") {
                LoginScreen {
                    login()
                }
            }

            step("Navigate to people list") {
                ListOfPeopleScreen {
                    isLoaded()

                    offerApartment(apartment, personToOfferApartment, userToOfferApartment)
                }
            }

            step("Navigate to advert and offer apartment") {
                PersonAdvertScreen {
                    isLoaded()
                    checkPerson(userToOfferApartment)

                    offerApartment()
                    checkThatYouCantOfferedWithoutApartmentToast()
                }
            }
        }
    }

    private fun checkOfferedApartmentToast(userED: UserED) {
        val nameAndSurname = "${userED.name!!} ${userED.surname!!}"

        val messageExpected =
            appContext.getString(R.string.text_you_offer_apartment_to) + nameAndSurname

        checkToast(messageExpected)
    }

    private fun checkThatYouCanOfferedYourselfToast() {
        val messageExpected =
            "Вы не можете предложить квартиру самому себе"

        checkToast(messageExpected)
    }

    private fun checkThatYouCantOfferedWithoutApartmentToast() {
        val messageExpected =
            "У вас не добавлена квартира"

        checkToast(messageExpected)
    }

    private fun checkToast(message: String) {

        Espresso.onView(ViewMatchers.withText(message))
            .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))// Here we use decorView
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
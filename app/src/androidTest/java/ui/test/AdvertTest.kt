package ui.test

import androidx.test.espresso.Espresso
import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.mail.polis.MainActivity
import ui.data.Advert
import ui.data.User
import ui.screens.AdvertEditingScreen
import ui.screens.ListOfPeopleScreen
import ui.screens.LoginScreen
import ui.screens.SettingsScreen
import kotlin.random.Random

class AdvertTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val defaultUserInfo: User = User(
        name = "Иван",
        surname = "Иванов",
        phone = "89025563557",
        age = 38
    )

    private val testUser: User = User(
        name = "Илья",
        surname = "Солянкин",
        phone = "89082122231",
        age = Random(0).nextInt()
    )

    private val defaultAdvert: Advert = Advert(
        chip = listOf("Студия", "1 комната", "2 комнаты", "4 комнаты"),
        costFrom = 10000,
        costTo = 20000
    )

    private val testAdvert: Advert = Advert(
        chip = listOf("Студия", "1 комната", "2 комнаты", "4 комнаты"),
        costFrom = Random(999).nextInt(),
        costTo = Random(999).nextInt()
    )

//    @Test
//    fun test() {
//        before {
//            activityTestRule.launchActivity(null)
//            LoginScreen {
//                login()
//            }
//        }.after {
//            ListOfPeopleScreen {
//                navigateToSettings()
//                SettingsScreen {
//                    changeUserInfo(defaultUserInfo)
//                    editPerson.click()
//                    AdvertEditingScreen {
//                        changeAdvInfo(defaultAdvert)
//                    }
//                }
//            }
//        }.run {
//            step("Check start parameters in ad") {
//                ListOfPeopleScreen {
//                    peopleRecycler {
//                        // Мое объявление висит всегда вторым. Надо переделать через childWith, но я не понял как он работает
//                        childAt<ListOfPeopleScreen.peopleRecyclerItem>(1) {
//                            personName.hasText(defaultUserInfo.name + " " + defaultUserInfo.surname)
//                            personAge.hasText(defaultUserInfo.age.toString() + " лет")
//                        }
//
//                        //Не работает
////                        val item = childWith<ListOfPeopleScreen.peopleRecyclerItem> {
////                            getViewMatcher().matches(
////                                withText(defaultUserInfo.name + " " + defaultUserInfo.surname)
////                            )
////                        }
////                        item.personName.hasText(defaultUserInfo.name + " " + defaultUserInfo.surname)
////                        item.personAge.hasText(defaultUserInfo.age.toString() + " лет")
//                    }
//                }
//            }
//
//            step("Navigate to settings") {
//                ListOfPeopleScreen {
//                    navigateToSettings()
//                }
//            }
//
//            step("Change user and click on \"Мое объявление\"") {
//                SettingsScreen {
//                    changeUserInfo(testUser)
//                    editPerson.click()
//                }
//            }
//
//            step("Change advert info") {
//                AdvertEditingScreen {
//                    changeAdvInfo(testAdvert)
//                    Thread.sleep(1000)
//                    Espresso.pressBack()
//                }
//            }
//
//            step("Navigate to settings") {
//                SettingsScreen {
//                    advertButton.click()
//                }
//            }
//
//            step("Check end parameters in ad") {
//                ListOfPeopleScreen {
//                    peopleRecycler {
//                        // Аналогично тому, что сверху
//                        childAt<ListOfPeopleScreen.peopleRecyclerItem>(1) {
//                            personName.hasText(testUser.name + " " + testUser.surname)
//                            personAge.hasText(testUser.age.toString() + " лет")
//                        }
//                    }
//                }
//            }
//        }
//    }
}

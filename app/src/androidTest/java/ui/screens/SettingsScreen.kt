package ui.screens

import androidx.test.espresso.action.ViewActions
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.ui.fragments.SettingsFragment

object SettingsScreen : KScreen<SettingsScreen>() {
    override val layoutId: Int? = R.layout.fragment_settings
    override val viewClass: Class<*>? = SettingsFragment::class.java

    private val nameTextBox = KEditText { withId(R.id.fragment_settings__et_name) }
    private val surnameTextBox = KEditText { withId(R.id.fragment_settings__et_surname) }

    //TODO есть проблемы, т.к в разметке испольуется не дефолтный TextBox
    private val phoneTextBox = KEditText { withId(R.id.fragment_settings__et_phone) }
    private val ageTextBox = KEditText { withId(R.id.fragment_settings__et_age) }

    private val editButton =
        KButton { withId(R.id.fragment_settings__button_edit) }

    private val listOfPeopleButton =
        KButton { withId(R.id.nav_graph__list_of_people) }



    val advertButton = KButton { withId(R.id.fragment_settings__edit_person) }

    val apartmentButton = KButton { withId(R.id.fragment_settings__edit_apartment) }

    fun navigateToListOfPeople() {
        listOfPeopleButton {
            isVisible()
            click()
        }
    }

    fun changeUserInfo(userED: UserED) {
        nameTextBox {
            isVisible()
            replaceText(userED.name!!)
        }

        surnameTextBox {
            isVisible()
            replaceText(userED.surname!!)
        }
//        phoneTextBox {
//            isVisible()
//            replaceText(user.phone)
//        }
        ageTextBox {
            isVisible()
            replaceText(userED.age.toString())
        }

        editButton {
            isVisible()
            click()
        }
    }

    fun checkUserInfo(userED: UserED) {
        nameTextBox {
            isVisible()
            hasText(userED.name!!)
        }

        surnameTextBox {
            isVisible()
            hasText(userED.surname!!)
        }

//        phoneTextBox {
//            isVisible()
//            hasText(user.phone)
//        }

        ageTextBox {
            isVisible()
            hasText(userED.age.toString())
        }
    }

    fun isLoaded() {
        nameTextBox {
            isVisible()
        }

        surnameTextBox {
            isVisible()
        }

        phoneTextBox {
            isVisible()
        }

        ageTextBox {
            isVisible()
        }
    }

    fun fillAllExceptName(userED: UserED) {
        clearField()

        surnameTextBox {
            isVisible()
            ViewActions.replaceText(userED.surname)
        }
//        phoneTextBox {
//            isVisible()
//            replaceText(user.phone)
//        }
        ageTextBox {
            isVisible()
            replaceText(userED.age.toString())
        }
    }

    fun fillAllExceptSurname(userED: UserED) {
        clearField()

        nameTextBox {
            isVisible()
            ViewActions.replaceText(userED.name)
        }
//        phoneTextBox {
//            isVisible()
//            replaceText(user.phone)
//        }
        ageTextBox {
            isVisible()
            replaceText(userED.age.toString())
        }
    }

    fun fillAllExceptPhone(userED: UserED) {
        clearField()

        nameTextBox {
            isVisible()
            ViewActions.replaceText(userED.name)
        }

        surnameTextBox {
            isVisible()
            ViewActions.replaceText(userED.surname)
        }

        ageTextBox {
            isVisible()
            replaceText(userED.age.toString())
        }
    }

    fun fillAllExceptAge(userED: UserED) {
        clearField()

        nameTextBox {
            isVisible()
            ViewActions.replaceText(userED.name)
        }

        surnameTextBox {
            isVisible()
            ViewActions.replaceText(userED.surname)
        }
//        phoneTextBox {
//            isVisible()
//            replaceText(user.phone)
//        }
    }

    fun clearField() {
        nameTextBox {
            clearText()
        }

        surnameTextBox {
            clearText()

        }
//        phoneTextBox {
//                        clearText()
//        }
        ageTextBox {
            clearText()
        }
    }

    fun clickEditButton() {
        editButton {
            isVisible()
            click()
        }
    }

    fun clickApartmentButton() {
        apartmentButton {
            isVisible()
            click()
        }
    }

    fun clickAdvertButton() {
        advertButton {
            isVisible()
            click()
        }
    }
}
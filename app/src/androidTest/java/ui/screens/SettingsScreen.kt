package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.SettingsFragment
import ui.data.User

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

    fun navigateToListOfPeople() {
        listOfPeopleButton {
            isVisible()
            click()
        }
    }

    fun changeUserInfo(user: User) {
        nameTextBox {
            isVisible()
            replaceText(user.name)
        }

        surnameTextBox {
            isVisible()
            replaceText(user.surname)
        }
//        phoneTextBox {
//            isVisible()
//            replaceText(user.phone)
//        }
        ageTextBox {
            isVisible()
            replaceText(user.age.toString())
        }

        editButton {
            isVisible()
            click()
        }
    }

    fun checkUserInfo(user: User) {
        nameTextBox {
            isVisible()
            hasText(user.name)
        }

        surnameTextBox {
            isVisible()
            hasText(user.surname)
        }

//        phoneTextBox {
//            isVisible()
//            hasText(user.phone)
//        }

        ageTextBox {
            isVisible()
            hasText(user.age.toString())
        }
    }

    val editPerson = KButton { withId(R.id.fragment_settings__edit_person) }

    val advertButton = KButton { withId(R.id.nav_graph__list_of_people) }
}
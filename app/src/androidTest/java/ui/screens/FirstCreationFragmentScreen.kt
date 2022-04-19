package ui.screens

import androidx.test.espresso.action.ViewActions.replaceText
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.ui.fragments.FirstCreationFragment

object FirstCreationFragmentScreen : KScreen<FirstCreationFragmentScreen>() {
    override val layoutId: Int = R.layout.fragment_filling_profile_info
    override val viewClass: Class<*> = FirstCreationFragment::class.java

    private val nameTextBox = KEditText { withId(R.id.fragment_filling_profile_info__name_et) }
    private val surnameTextBox =
        KEditText { withId(R.id.fragment_filling_profile_info__surname_et) }

    //TODO есть проблемы, т.к в разметке испольуется не дефолтный TextBox
    private val phoneTextBox = KEditText { withId(R.id.fragment_filling_profile_info__phone_et) }
    private val ageTextBox = KEditText { withId(R.id.fragment_filling_profile_info__age_et) }
    private val continueButton =
        KButton { withId(R.id.fragment_filling_profile_info_continue__btn) }

    fun clickContinueButton() {
        continueButton {
            isVisible()
            click()
        }
    }

    fun fillInfo(userED: UserED) {
        clearField()

        nameTextBox {
            isVisible()
            replaceText(userED.name)
        }

        surnameTextBox {
            isVisible()
            replaceText(userED.surname)
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

    fun fillAllExceptName(userED: UserED) {
        clearField()

        surnameTextBox {
            isVisible()
            replaceText(userED.surname)
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
            replaceText(userED.name)
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
            replaceText(userED.name)
        }

        surnameTextBox {
            isVisible()
            replaceText(userED.surname)
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
            replaceText(userED.name)
        }

        surnameTextBox {
            isVisible()
            replaceText(userED.surname)
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
}
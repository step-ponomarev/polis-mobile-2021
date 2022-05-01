package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import ru.mail.polis.R
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.ui.fragments.PersonAnnouncementFragment

object PersonAdvertScreen : KScreen<PersonAdvertScreen>() {
    override val layoutId: Int? = R.layout.fragment_person_announcement
    override val viewClass: Class<*>? = PersonAnnouncementFragment::class.java

    private val offerButton =
        KButton { withId(R.id.fragment_person_announcement__button_show_number) }
    private val nameTextView = KTextView { withId(R.id.component_person_header__name) }

    fun navigateToSettings() {
        ListOfPeopleScreen.settingsButton {
            isVisible()
            isClickable()
            click()
        }
    }

    fun isLoaded() {
        offerButton {
            isVisible()
        }

        nameTextView {
            isVisible()
            hasAnyText()
        }
    }


    fun checkPerson(userED: UserED) {
        val nameAndSurname = "${userED.name!!} ${userED.surname!!}"
        nameTextView {
            hasText(nameAndSurname)
        }
    }

    fun offerApartment() {
        offerButton {
            isVisible()
            click()
        }
    }
}
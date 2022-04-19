package ui.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.spinner.KSpinner
import io.github.kakaocup.kakao.spinner.KSpinnerItem
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import ru.mail.polis.R
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.ui.fragments.advert.AdvertCreationFragment

object AddAdvertScreen : KScreen<AddAdvertScreen>() {
    override val layoutId: Int = R.layout.fragment_advert_creation
    override val viewClass: Class<*> = AdvertCreationFragment::class.java

    val metroSpinner = KSpinner(
        builder = { withId(R.id.fragment_advert_creation__spinner) },
        itemTypeBuilder = { itemType(::KSpinnerItem) }
    )
    val roomChipGroup = KChipGroup { withId(R.id.component_rooms__chip_group) }
    val costFromEditText = KEditText { withId(R.id.fragment_advert_creation__from_price_et) }
    val costToEditText = KEditText { withId(R.id.fragment_advert_creation__to_price_et) }
    val squareFromEditText =
        KEditText { withId(R.id.fragment_advert_creation__from_metres_et) }
    val squareToEditText =
        KEditText { withId(R.id.fragment_advert_creation__to_metres_et) }
    val addButton = KButton { withId(R.id.fragment_advert_creation__continue_btn) }

    val aboutMeEditText = KEditText { withId(R.id.fragment_advert_creation__about_me_et) }

    val nameTextView = KTextView { withId(R.id.component_person_header__name) }
    val ageTextView = KTextView { withId(R.id.component_person_header__age) }


    fun isLoaded() {
        metroSpinner {
            isVisible()
        }

        roomChipGroup {
            isVisible()
        }

        costFromEditText {
            isVisible()
        }
        costToEditText {
            isVisible()
        }

        squareFromEditText {
            isVisible()
        }

        squareToEditText {
            isVisible()
        }

        aboutMeEditText {
            isVisible()
        }

        nameTextView {
            isVisible()
        }

        ageTextView {
            isVisible()
        }

        addButton {
            isVisible()
        }
    }

    fun fillAllFields(personED: PersonED) {
        val rooms = personED.rooms
        val metresFrom = personED.metresFrom
        val metresTo = personED.metresTo
        val moneyFrom = personED.moneyFrom
        val moneyTo = personED.moneyTo
        val description = personED.description
        val stationName = personED.metro!!.stationName


        Espresso.onView(ViewMatchers.withId(R.id.fragment_advert_creation__spinner))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(stationName)).perform(ViewActions.click())

        roomChipGroup {
            rooms.forEach {
                this.selectChip(it.label)
            }
        }

        squareFromEditText {
            replaceText(metresFrom.toString())
        }

        squareToEditText {
            replaceText(metresTo.toString())
        }

        costFromEditText {
            replaceText(moneyFrom.toString())
        }

        costToEditText {
            replaceText(moneyTo.toString())
        }

        aboutMeEditText {
            replaceText(description!!)
        }

    }

    fun clickAddButton() {
        addButton {
            click()
        }
    }

    fun clearFields() {

        //cant clear
        roomChipGroup {
//            rooms.forEach {
//                this.selectChip(it.label)
//            }
        }

        squareFromEditText {
            clearText()
        }

        squareToEditText {
            clearText()
        }

        costFromEditText {
            clearText()
        }

        costToEditText {
            clearText()
        }

        aboutMeEditText {
            clearText()
        }
    }

    fun fillAllExceptNumberRooms(personED: PersonED) {
        clearFields()

        val rooms = personED.rooms
        val metresFrom = personED.metresFrom
        val metresTo = personED.metresTo
        val moneyFrom = personED.moneyFrom
        val moneyTo = personED.moneyTo
        val description = personED.description
        val stationName = personED.metro!!.stationName


        Espresso.onView(ViewMatchers.withId(R.id.fragment_advert_creation__spinner))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(stationName)).perform(ViewActions.click())

        squareFromEditText {
            replaceText(metresFrom.toString())
        }

        squareToEditText {
            replaceText(metresTo.toString())
        }

        costFromEditText {
            replaceText(moneyFrom.toString())
        }

        costToEditText {
            replaceText(moneyTo.toString())
        }

        aboutMeEditText {
            replaceText(description!!)
        }
    }

    fun fillAllExceptCost(personED: PersonED) {
        clearFields()

        val rooms = personED.rooms
        val metresFrom = personED.metresFrom
        val metresTo = personED.metresTo
        val moneyFrom = personED.moneyFrom
        val moneyTo = personED.moneyTo
        val description = personED.description
        val stationName = personED.metro!!.stationName


        Espresso.onView(ViewMatchers.withId(R.id.fragment_advert_creation__spinner))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(stationName)).perform(ViewActions.click())

        roomChipGroup {
            rooms.forEach {
                this.selectChip(it.label)
            }
        }

        squareFromEditText {
            replaceText(metresFrom.toString())
        }

        squareToEditText {
            replaceText(metresTo.toString())
        }

        aboutMeEditText {
            replaceText(description!!)
        }
    }

    fun fillAllExceptMetres(personED: PersonED) {
        clearFields()

        val rooms = personED.rooms
        val metresFrom = personED.metresFrom
        val metresTo = personED.metresTo
        val moneyFrom = personED.moneyFrom
        val moneyTo = personED.moneyTo
        val description = personED.description
        val stationName = personED.metro!!.stationName


        Espresso.onView(ViewMatchers.withId(R.id.fragment_advert_creation__spinner))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(stationName)).perform(ViewActions.click())

        roomChipGroup {
            rooms.forEach {
                this.selectChip(it.label)
            }
        }

        costFromEditText {
            replaceText(moneyFrom.toString())
        }

        costToEditText {
            replaceText(moneyTo.toString())
        }

        aboutMeEditText {
            replaceText(description!!)
        }
    }
}
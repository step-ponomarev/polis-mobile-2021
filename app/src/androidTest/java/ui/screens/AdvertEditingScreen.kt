package ui.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.spinner.KSpinner
import io.github.kakaocup.kakao.spinner.KSpinnerItem
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.CoreMatchers.containsString
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.ui.fragments.advert.AdvertEditingFragment

object AdvertEditingScreen : KScreen<AdvertEditingScreen>() {
    override val layoutId: Int? = R.layout.fragment_advert_editing
    override val viewClass: Class<*>? = AdvertEditingFragment::class.java

    val metroSpinner = KSpinner(
        builder = { withId(R.id.fragment_advert_creation__spinner) },
        itemTypeBuilder = { itemType(::KSpinnerItem) }
    )
    val roomsChipGroup = KChipGroup { withId(R.id.component_rooms__chip_group) }
    val costFromEditText = KEditText { withId(R.id.fragment_advert_creation__from_price_et) }
    val costToEditText = KEditText { withId(R.id.fragment_advert_creation__to_price_et) }
    val metresFromEditText = KEditText { withId(R.id.fragment_advert_creation__from_metres_et) }
    val metresToEditText = KEditText { withId(R.id.fragment_advert_creation__to_metres_et) }
    val editAdvertButton = KButton { withId(R.id.fragment_advert_editing__continue_btn) }
    val aboutMeEditText = KEditText { withId(R.id.fragment_advert_creation__about_me_et) }

    val nameTextView = KTextView { withId(R.id.component_person_header__name) }
    val ageTextView = KTextView { withId(R.id.component_person_header__age) }


    fun changeApartmentInfo(apartmentED: ApartmentED) {
//        //Без ожидания все приложение крашится
//        Thread.sleep(1000)
//
//        findChipGroup {
//            isVisible()
//            for (chip in advert.chip) {
//                selectChip(chip)
//            }
//        }
//
//        costFromEditText {
//            isVisible()
//            replaceText(advert.costFrom.toString())
//        }
//
//        costToEditText {
//            isVisible()
//            replaceText(advert.costTo.toString())
//        }
//
//        editAdvertButton {
//            isVisible()
//            click()
//        }
    }

    fun isLoaded() {
        metroSpinner {
            isVisible()
        }

        roomsChipGroup {
            isVisible()
        }

        costFromEditText {
            isVisible()
            hasAnyText()
        }

        costToEditText {
            isVisible()
            hasAnyText()
        }

        metresFromEditText {
            isVisible()
            hasAnyText()
        }

        metresToEditText {
            isVisible()
            hasAnyText()
        }

        editAdvertButton {
            isVisible()
        }

        aboutMeEditText {
            isVisible()
            hasAnyText()
        }
    }

    fun checkAdvertInfo(personED: PersonED) {
        val rooms = personED.rooms
        val metresFrom = personED.metresFrom
        val metresTo = personED.metresTo
        val moneyFrom = personED.moneyFrom
        val moneyTo = personED.moneyTo
        val description = personED.description
        val stationName = personED.metro!!.stationName


        Espresso.onView(ViewMatchers.withId(R.id.fragment_advert_creation__spinner))
            .check(matches(withSpinnerText(containsString(personED.metro!!.stationName))))


        roomsChipGroup {
            rooms.forEach {
                this.selectChip(it.label)
            }
        }

        metresFromEditText {
            hasText(metresFrom.toString())
        }

        metresToEditText {
            hasText(metresTo.toString())
        }

        costFromEditText {
            hasText(moneyFrom.toString())
        }

        costToEditText {
            hasText(moneyTo.toString())
        }

        aboutMeEditText {
            hasText(description!!)
        }
    }

    fun clickEditButton() {
        editAdvertButton {
            isVisible()
            click()
        }
    }

    fun changeAdvertInfo(personED: PersonED) {
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

        roomsChipGroup {
//            rooms.forEach {
//                this.selectChip(it.label)
//            }
        }

        metresFromEditText {
            replaceText(metresFrom.toString())
        }

        metresToEditText {
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

        clickEditButton()
    }

    fun clearFields() {

        //cant clear
        AddAdvertScreen.roomChipGroup {
//            rooms.forEach {
//                this.selectChip(it.label)
//            }
        }

        AddAdvertScreen.squareFromEditText {
            clearText()
        }

        AddAdvertScreen.squareToEditText {
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
        AddAdvertScreen.clearFields()

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

        AddAdvertScreen.squareFromEditText {
            replaceText(metresFrom.toString())
        }

        AddAdvertScreen.squareToEditText {
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
        AddAdvertScreen.clearFields()

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

        AddAdvertScreen.roomChipGroup {
            rooms.forEach {
                this.selectChip(it.label)
            }
        }

        AddAdvertScreen.squareFromEditText {
            replaceText(metresFrom.toString())
        }

        AddAdvertScreen.squareToEditText {
            replaceText(metresTo.toString())
        }

        aboutMeEditText {
            replaceText(description!!)
        }
    }

    fun fillAllExceptMetres(personED: PersonED) {
        AddAdvertScreen.clearFields()

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

        AddAdvertScreen.roomChipGroup {
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
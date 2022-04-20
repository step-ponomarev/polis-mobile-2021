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
import org.hamcrest.CoreMatchers.containsString
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.ui.fragments.apartment.EditApartmentFragment

object ApartmentEditingScreen : KScreen<ApartmentEditingScreen>() {
    override val layoutId: Int = R.layout.fragment_edit_apartment
    override val viewClass: Class<*> = EditApartmentFragment::class.java

    val metroSpinner = KSpinner(
        builder = { withId(R.id.component_apartment_info__spinner) },
        itemTypeBuilder = { itemType(::KSpinnerItem) }
    )
    val roomChipGroup = KChipGroup { withId(R.id.component_rooms__chip_group) }
    val costEditText = KEditText { withId(R.id.component_apartment_info__set_cost_et) }
    val squareEditText =
        KEditText { withId(R.id.component_apartment_info__set_squared_metres_et) }
    val editButton = KButton { withId(R.id.edit_button) }


    fun isLoaded() {
        metroSpinner {
            isVisible()
        }

        roomChipGroup {
            isVisible()
        }

        costEditText {
            hasAnyText()
            isVisible()
        }
//hastext т.к подгружается
        squareEditText {
            hasAnyText()
            isVisible()
        }

        editButton {
            isVisible()
        }
    }

    fun changeApartmentInfo(apartment: ApartmentED) {

        val stationName = apartment.metro!!.stationName
        val cost = apartment.apartmentCosts
        val square = apartment.apartmentSquare
        val rooms = apartment.roomCount!!.label

        Espresso.onView(ViewMatchers.withId(R.id.component_apartment_info__spinner))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(stationName)).perform(ViewActions.click())

        roomChipGroup {
        }

        costEditText {
            replaceText(cost.toString())
        }

        squareEditText {
            replaceText(square.toString())
        }

        editButton.click()
    }

    fun checkApartmentInfo(updatedApartment: ApartmentED) {

        Espresso.onView(ViewMatchers.withId(R.id.component_apartment_info__spinner))
            .check(matches(withSpinnerText(containsString(updatedApartment.metro!!.stationName))))

        roomChipGroup {
            isChipSelected(updatedApartment.roomCount!!.label)
        }

        costEditText {
            hasText(updatedApartment.apartmentCosts.toString())
        }

        squareEditText {
            hasText(updatedApartment.apartmentSquare.toString())
        }
    }


    fun fillAllExceptCost(apartmentED: ApartmentED) {
        clearFields()

        val stationName = apartmentED.metro?.stationName ?: throw IllegalStateException()
        val rooms = apartmentED.roomCount?.label ?: throw IllegalStateException()
        val cost = apartmentED.apartmentCosts ?: throw IllegalStateException()
        val metres = apartmentED.apartmentSquare ?: throw IllegalStateException()

        Espresso.onView(ViewMatchers.withId(R.id.component_apartment_info__spinner))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(stationName)).perform(ViewActions.click())



        roomChipGroup {
            selectChip(rooms)

            isChipSelected(rooms)
        }

        squareEditText {
            replaceText(metres.toString())
        }
    }

    fun fillAllExceptMetres(apartmentED: ApartmentED) {
        clearFields()
        val stationName = apartmentED.metro?.stationName ?: throw IllegalStateException()
        val rooms = apartmentED.roomCount?.label ?: throw IllegalStateException()
        val cost = apartmentED.apartmentCosts ?: throw IllegalStateException()
        val metres = apartmentED.apartmentSquare ?: throw IllegalStateException()

        Espresso.onView(ViewMatchers.withId(R.id.component_apartment_info__spinner))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(stationName)).perform(ViewActions.click())


        roomChipGroup {
            selectChip(rooms)

            isChipSelected(rooms)
        }

        costEditText {
            replaceText(cost.toString())
        }
    }

    private fun clearFields() {

        costEditText {
            clearText()
        }

        squareEditText {
            clearText()
        }
    }

    fun clickEditButton() {
        editButton {
            isVisible()
            click()
        }
    }
}
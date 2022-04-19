package ui.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.spinner.KSpinner
import io.github.kakaocup.kakao.spinner.KSpinnerItem
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.ui.fragments.apartment.AddApartmentFragment

object AddApartmentScreen : KScreen<AddApartmentScreen>() {
    override val layoutId: Int = R.layout.fragment_add_apartment
    override val viewClass: Class<*> = AddApartmentFragment::class.java

    val metroSpinner = KSpinner(
        builder = { withId(R.id.component_apartment_info__spinner) },
        itemTypeBuilder = { itemType(::KSpinnerItem) }
    )
    val roomChipGroup = KChipGroup { withId(R.id.component_rooms__chip_group) }
    val costEditText = KEditText { withId(R.id.component_apartment_info__set_cost_et) }
    val squareEditText =
        KEditText { withId(R.id.component_apartment_info__set_squared_metres_et) }
    val addButton = KButton { withId(R.id.add_button) }


    fun isLoaded() {
        metroSpinner {
            isVisible()
        }

        roomChipGroup {
            isVisible()
        }

        costEditText {
            isVisible()
        }

        squareEditText {
            isVisible()
        }

        addButton {
            isVisible()
        }
    }

    fun fillApartmentInfo(apartmentED: ApartmentED) {
        val stationName = apartmentED.metro?.stationName ?: throw IllegalStateException()
        val rooms = apartmentED.roomCount?.label ?: throw IllegalStateException()
        val cost = apartmentED.apartmentCosts ?: throw IllegalStateException()
        val metres = apartmentED.apartmentSquare ?: throw IllegalStateException()

        onView(withId(R.id.component_apartment_info__spinner)).perform(click())
        onView(withText(stationName)).perform(click())


        roomChipGroup {
            selectChip(rooms)

            isChipSelected(rooms)
        }

        costEditText {
            replaceText(cost.toString())
        }

        squareEditText {
            replaceText(metres.toString())
        }


    }

    fun clickAddButton() {
        addButton {
            isVisible()
            click()
        }
    }

    fun fillAllExceptNumberRooms(apartmentED: ApartmentED) {
        clearFields()

        val stationName = apartmentED.metro?.stationName ?: throw IllegalStateException()
        val rooms = apartmentED.roomCount?.label ?: throw IllegalStateException()
        val cost = apartmentED.apartmentCosts ?: throw IllegalStateException()
        val metres = apartmentED.apartmentSquare ?: throw IllegalStateException()

        onView(withId(R.id.component_apartment_info__spinner)).perform(click())
        onView(withText(stationName)).perform(click())


        costEditText {
            replaceText(cost.toString())
        }

        squareEditText {
            replaceText(metres.toString())
        }
    }

    fun fillAllExceptCost(apartmentED: ApartmentED) {
        clearFields()

        val stationName = apartmentED.metro?.stationName ?: throw IllegalStateException()
        val rooms = apartmentED.roomCount?.label ?: throw IllegalStateException()
        val cost = apartmentED.apartmentCosts ?: throw IllegalStateException()
        val metres = apartmentED.apartmentSquare ?: throw IllegalStateException()

        onView(withId(R.id.component_apartment_info__spinner)).perform(click())
        onView(withText(stationName)).perform(click())



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

        onView(withId(R.id.component_apartment_info__spinner)).perform(click())
        onView(withText(stationName)).perform(click())


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
}
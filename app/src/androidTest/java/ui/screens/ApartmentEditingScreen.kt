package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.spinner.KSpinner
import io.github.kakaocup.kakao.spinner.KSpinnerItem
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
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
            isVisible()
        }

        squareEditText {
            isVisible()
        }

        editButton {
            isVisible()
        }
    }
}
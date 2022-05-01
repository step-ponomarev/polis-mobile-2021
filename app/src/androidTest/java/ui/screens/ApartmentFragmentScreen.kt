package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.edit.KEditText
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.apartment.ApartmentFragment

object ApartmentFragmentScreen : KScreen<ApartmentFragmentScreen>() {
    override val layoutId: Int? = R.layout.component_person_advert_info
    override val viewClass: Class<*>? = ApartmentFragment::class.java

    //    val Spinner = KSpinner { withId(R.id.component_apartment_info__spinner) }
    val findChipGroup = KChipGroup { withId(R.id.component_rooms__chip_group) }
    val findCostEditText = KEditText { withId(R.id.component_apartment_info__set_cost_et) }
    val findSquareEditText =
        KEditText { withId(R.id.component_apartment_info__set_squared_metres_et) }

}
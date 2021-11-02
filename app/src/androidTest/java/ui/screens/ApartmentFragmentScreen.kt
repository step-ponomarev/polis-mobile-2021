package ui.screens

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.spinner.KSpinner
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.apartment.ApartmentFragment

object ApartmentFragmentScreen : KScreen<ApartmentFragmentScreen>() {
    override val layoutId: Int? = R.layout.component_person_advert_info
    override val viewClass: Class<*>? = ApartmentFragment::class.java

//    val findSpinner = KSpinner { withId(R.id.component_apartment_info__spinner)}
    val findMetroCircleIv = KImageView { withId(R.id.component_apartment_info__circle)}
    val findChipGroup = KChipGroup {withId(R.id.component_rooms__chip_group)}
    val findCostEditText = KEditText {withId(R.id.component_apartment_info__set_cost_et)}
    val findSquareEditText = KEditText {withId(R.id.component_apartment_info__set_squared_metres_et)}
}
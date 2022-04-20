package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.ui.fragments.advert.AdvertEditingFragment
import ui.data.Advert

object AdvertEditingScreen : KScreen<AdvertEditingScreen>() {
    override val layoutId: Int? = R.layout.fragment_advert_editing
    override val viewClass: Class<*>? = AdvertEditingFragment::class.java

    val editAdvertButton = KButton { withId(R.id.fragment_advert_editing__continue_btn) }
    val findChipGroup = KChipGroup { withId(R.id.component_rooms__chip_group) }
    val costFromEditText = KEditText { withId(R.id.fragment_advert_creation__from_price_et) }
    val costToEditText = KEditText { withId(R.id.fragment_advert_creation__to_price_et) }

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

    }
}
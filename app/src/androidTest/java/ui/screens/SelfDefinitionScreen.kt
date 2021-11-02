package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.SelfDefinitionFragment

object SelfDefinitionScreen : KScreen<SelfDefinitionScreen>() {
    override val layoutId: Int? = R.layout.fragment_self_definition
    override val viewClass: Class<*>? = SelfDefinitionFragment::class.java


    val findAppartmentButton =
        KButton { withId(R.id.fragment_self_definition__button_find_apartment) }
    val rentApartmentButton =
        KButton { withId(R.id.fragment_self_definition__button_rent_apartment) }
}
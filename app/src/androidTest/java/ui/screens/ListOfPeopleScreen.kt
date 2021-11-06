package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.ListOfPeopleFragment

object ListOfPeopleScreen: KScreen<ListOfPeopleScreen>() {
    override val layoutId: Int? = R.layout.fragment_person_announcement
    override val viewClass: Class<*>? = ListOfPeopleFragment::class.java

    val settingsButton =
        KButton { withId(R.id.nav_graph__settings_fragment) }


}
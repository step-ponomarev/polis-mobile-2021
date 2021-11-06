package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.SettingsFragment

object SettingsScreen : KScreen<SettingsScreen>() {
    override val layoutId: Int? = R.layout.fragment_settings
    override val viewClass: Class<*>? = SettingsFragment::class.java

    val nameTextBox = KEditText { withId(R.id.fragment_settings__et_name) }
    val surnameTextBox = KEditText { withId(R.id.fragment_settings__et_surname) }
    val phoneTextBox = KEditText { withId(R.id.fragment_settings__et_phone) }
    val ageTextBox = KEditText { withId(R.id.fragment_settings__et_age) }

    val editButton =
        KButton { withId(R.id.fragment_settings__button_edit) }
}
package ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import ru.mail.polis.R
import ru.mail.polis.ui.fragments.CustomDialogFragment

object ApartmentDialog : KScreen<ApartmentDialog>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*> = CustomDialogFragment::class.java

    val textDialog = KTextView { withText(R.string.dialog_fragment_message_add_apartment) }
    val title = KTextView { withText(R.string.dialog_fragment_title_add_apartment) }
    val cancelButton = KButton { withText(R.string.dialog_fragment_cancel_button_text) }
    val addButton = KButton { withText(R.string.dialog_fragment_add_button_text) }

    fun isLoaded() {
        textDialog {
            isVisible()
        }

        title {
            isVisible()
        }

        cancelButton {
            isVisible()
        }

        addButton {
            isVisible()
        }
    }

    fun clickAdd() {
        addButton {
            isVisible()
            click()
        }
    }
}
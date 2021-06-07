package ru.mail.polis.list.of.propose

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mail.polis.dao.propose.ProposeStatus

@Parcelize
class ProposeView(
    var ownerEmail: String?,
    var renterEmail: String?,
    var status: ProposeStatus?
) : Parcelable {
    class Builder private constructor() {

        var ownerEmail: String? = null
        var renterEmail: String? = null
        var status: ProposeStatus? = null

        companion object {
            fun createBuilder(): Builder {
                return Builder()
            }
        }

        fun ownerEmail(ownerEmail: String): Builder {
            this.ownerEmail = ownerEmail
            return this
        }

        fun renterEmail(renterEmail: String): Builder {
            this.renterEmail = renterEmail
            return this
        }

        fun status(status: ProposeStatus?): Builder {
            this.status = status
            return this
        }

        fun build(): ProposeView {
            return ProposeView(
                ownerEmail,
                renterEmail,
                status
            )
        }
    }
}

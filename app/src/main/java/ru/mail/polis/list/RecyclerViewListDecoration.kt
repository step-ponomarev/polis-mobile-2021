package ru.mail.polis.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewListDecoration() : RecyclerView.ItemDecoration() {
    companion object {
        const val VERTICAL_MARGIN: Int = 40
        const val HORIZONTAL_MARGIN: Int = 40
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = VERTICAL_MARGIN
            }

            left = HORIZONTAL_MARGIN
            right = HORIZONTAL_MARGIN
            bottom = VERTICAL_MARGIN
        }
    }
}

package ru.mail.polis.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewListDecoration() : RecyclerView.ItemDecoration() {
    companion object {
        const val MARGIN: Int = 100;
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != 0) {
                top = MARGIN
            }
        }
    }
}
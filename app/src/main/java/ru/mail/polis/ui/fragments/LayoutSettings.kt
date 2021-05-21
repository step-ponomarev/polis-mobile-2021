package ru.mail.polis.ui.fragments

import android.view.ViewGroup

object LayoutSettings {
    fun getLayoutParams(width: Int, height: Int): ViewGroup.LayoutParams {
        val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        params.width = width
        params.height = height

        return params
    }
}

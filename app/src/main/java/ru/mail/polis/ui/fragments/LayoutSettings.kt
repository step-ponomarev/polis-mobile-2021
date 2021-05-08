package ru.mail.polis.ui.fragments

import android.view.ViewGroup

object LayoutSettings {
    fun getLayoutParams(width: Int, height: Int): ViewGroup.LayoutParams {
        val prm: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        prm.width = width
        prm.height = height

        return prm
    }
}
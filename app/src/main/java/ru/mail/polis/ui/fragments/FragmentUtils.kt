package ru.mail.polis.ui.fragments

import ru.mail.polis.exceptions.NumberInEditTextException

object FragmentUtils {
    const val BUNDLE_IMAGE_KEY = "image"

    @Throws(NumberInEditTextException::class)
    fun checkNumberInString(text: String) {
        for (char in text) {
            if (!char.isLetter()) {
                throw NumberInEditTextException("Имя не должно сожержать ничего кроме букв")
            }
        }
    }
}

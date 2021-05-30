package ru.mail.polis.tags

import ru.mail.polis.R

enum class Tags(val activeImage: Int, val defaultImage: Int) {
    PETS(R.drawable.ic_paw, R.drawable.ic_paw_not_click),
    NOISE(R.drawable.ic_drum, R.drawable.ic_drum_not_click),
    KIDS(R.drawable.ic_kid, R.drawable.ic_kid_not_click),
    CIGARETTE(R.drawable.ic_cigarette, R.drawable.ic_cigarette_not_click);

    companion object {
        fun from(image: Int): Tags {
            values().forEach {
                if (it.activeImage == image || it.defaultImage == image) {
                    return it
                }
            }
            throw IllegalArgumentException("There is no tag with such resource image code $image")
        }
    }
}

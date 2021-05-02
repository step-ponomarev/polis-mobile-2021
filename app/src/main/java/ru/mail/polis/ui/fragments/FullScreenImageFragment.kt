package ru.mail.polis.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import ru.mail.polis.R


class FullScreenImageFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val iv = view.findViewById<ImageView>(R.id.fragment__fdadas)

        if (bundle != null) {
            iv.setImageBitmap(bundle.get("image") as Bitmap)
        }
    }
}
package ru.mail.polis.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import ru.mail.polis.R

class FullScreenImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val iv = view.findViewById<ImageView>(R.id.fragment_full_screen_image__iv)

        if (bundle != null) {
            iv.setImageBitmap(bundle.get(FragmentUtils.BUNDLE_IMAGE_KEY) as Bitmap)
        }
    }
}

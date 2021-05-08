//package ru.mail.polis.utils
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.ImageDecoder
//import android.net.Uri
//import android.os.Build
//import androidx.core.content.ContentProviderCompat.requireContext
//
//class UriDecoder : Decoder {
//    override fun decode(uri: Uri): Bitmap {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            val image =
//                ImageDecoder.createSource(requireContext().contentResolver, uri)
//            ImageDecoder.decodeBitmap(image)
//        } else {
//            val imageStream =
//                requireActivity().contentResolver.openInputStream(uri)
//            BitmapFactory.decodeStream(imageStream)
//        }
//    }
//}
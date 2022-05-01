package ru.mail.polis.unit

import android.graphics.BitmapFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.mail.polis.R
import ru.mail.polis.converter.Converter

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(27))
class ConverterTest: BaseTest() {

    @Test
    fun converterTest() {
        val icon = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.picture);


        val firstBitmap = icon
        val secondBitmap = icon


        val firstArray = Converter.bitmapToInputStream(firstBitmap)
        val secondArray = Converter.bitmapToInputStream(secondBitmap)


        Assert.assertArrayEquals(firstArray, secondArray)
    }
}
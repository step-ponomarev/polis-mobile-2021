package ru.mail.polis

import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.R
import ru.mail.polis.converter.Converter


class ConverterTest {

    @Test
    fun converterTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val icon = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.picture);


        val firstBitmap = icon
        val secondBitmap = icon


        val firstArray = Converter.bitmapToInputStream(firstBitmap)
        val secondArray = Converter.bitmapToInputStream(secondBitmap)


        Assert.assertArrayEquals(firstArray, secondArray)
    }
}
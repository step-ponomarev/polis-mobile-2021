package ru.mail.polis.unit.decoder

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.Mockito
import ru.mail.polis.TestData
import ru.mail.polis.decoder.UriDecoder

class DecoderTest {
    private val uri = "text://somefile"
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val testBitmap = TestData.getBitMap(appContext)


    @Test
    fun test() {
        val mock = Mockito.mock(UriDecoder::class.java)
//        val imageDecoder = DecoderFactory.getImageDecoder(appContext.contentResolver)

        Mockito.`when`(mock.decode(any(Uri::class.java))).thenReturn(testBitmap)

        val bitmap = mock.decode(Uri.parse(uri))

//        val decode = imageDecoder.decode(Uri.parse(uri))

        assertTrue(bitmap.equals(testBitmap))
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}
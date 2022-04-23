package ru.mail.polis.unit

import junit.framework.TestCase.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.mail.polis.R
import ru.mail.polis.metro.Metro
import java.lang.IllegalArgumentException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(27))
class MetroTest: BaseTest() {
    private val metroName = "Девяткино"
    private val colorConst = R.color.metro_red
    private val incorrectName = "Девяткина"

    @Test
    fun foundTest() {
        val from = Metro.from(metroName)

        assert(from == Metro.DEVYATKINO)
        assert(from.branchColor == colorConst)
        assert(from.stationName == metroName)
    }

    @Test
    fun notFoundTest() {
        var found = true
        try {
            val from = Metro.from(incorrectName)
        } catch (e: Exception) {
            found = false
            assert(e is IllegalArgumentException)
        }

        assertFalse(found)
    }

}
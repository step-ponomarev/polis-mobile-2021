package ru.mail.polis

import junit.framework.TestCase.assertFalse
import org.junit.Test
import ru.mail.polis.metro.Metro
import java.lang.IllegalArgumentException

class MetroTest {
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
package ru.mail.polis

import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertFalse
import org.junit.Test
import ru.mail.polis.tags.Tags

class TagsTest {
    @Test
    fun test() {
        val from = Tags.from(R.drawable.ic_paw)

        assert(from == Tags.PETS)
    }

    @Test
    fun notFoundTest() {
        var found = true
        try {
            val from = Tags.from(Int.MIN_VALUE)
        } catch (e: Exception) {
            found = false
            assert(e is IllegalArgumentException)
        }

        assertFalse(found)
    }
}
package ru.mail.polis.unit

import junit.framework.TestCase.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.mail.polis.R
import ru.mail.polis.tags.Tags

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(27))
class TagsTest: BaseTest() {
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
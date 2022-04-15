package ru.mail.polis

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.helpers.getAgeString

class AgeTest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @Test
    fun godaTest() {
        val ageStringWithoutContext = getAgeString(24, appContext)

        Assert.assertTrue(ageStringWithoutContext.compareTo("24 года") == 0)
    }

    @Test
    fun letTest() {
        val ageStringWithoutContext = getAgeString(20, appContext)

        Assert.assertTrue(ageStringWithoutContext.compareTo("20 лет") == 0)
    }

    @Test
    fun godTest() {
        val ageStringWithoutContext = getAgeString(21, appContext)

        Assert.assertTrue(ageStringWithoutContext.compareTo("21 год") == 0)
    }
}
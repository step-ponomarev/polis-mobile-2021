package ru.mail.polis

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.mail.polis.helpers.getAgeString

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(27))
class AgeTest : BaseTest() {

    @Test
    fun godaTest() {

        val ageStringWithoutContext = getAgeString(24, appContext)
        val godaString = appContext.getString(R.string.age_advert_goda)

        Assert.assertTrue(ageStringWithoutContext.compareTo("24 $godaString") == 0)
    }

    @Test
    fun letTest() {
        val ageStringWithoutContext = getAgeString(20, appContext)
        val letString = appContext.getString(R.string.age_advert_let)


        Assert.assertTrue(ageStringWithoutContext.compareTo("20 $letString") == 0)
    }

    @Test
    fun godTest() {
        val ageStringWithoutContext = getAgeString(21, appContext)
        val godString = appContext.getString(R.string.age_advert_god)


        Assert.assertTrue(ageStringWithoutContext.compareTo("21 $godString") == 0)
    }
}
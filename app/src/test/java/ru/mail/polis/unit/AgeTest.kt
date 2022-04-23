package ru.mail.polis.unit

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.mail.polis.R
import ru.mail.polis.helpers.getAgeString

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(27))
class AgeTest : BaseTest() {

    @Test
    fun godaTest() {

        val age: Long = 24
        val ageStringWithoutContext = getAgeString(age, appContext)
        val godaString = appContext.getString(R.string.age_advert_goda)

        Assert.assertTrue(ageStringWithoutContext.compareTo("$age $godaString") == 0)
    }

    @Test
    fun letTest() {
        val age: Long = 20
        val ageStringWithoutContext = getAgeString(age, appContext)
        val letString = appContext.getString(R.string.age_advert_let)


        Assert.assertTrue(ageStringWithoutContext.compareTo("$age $letString") == 0)
    }

    @Test
    fun godTest() {
        val age: Long = 21
        val ageStringWithoutContext = getAgeString(age, appContext)
        val godString = appContext.getString(R.string.age_advert_god)


        Assert.assertTrue(ageStringWithoutContext.compareTo("$age $godString") == 0)
    }

    @Test
    fun oldUser() {
        val age = 200L
        val ageString = getAgeString(age, appContext)
        val letString = appContext.getString(R.string.age_advert_let)


        Assert.assertTrue(ageString.compareTo("$age $letString") == 0)
    }
}
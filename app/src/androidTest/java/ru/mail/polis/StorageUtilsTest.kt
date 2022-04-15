package ru.mail.polis

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import ru.mail.polis.utils.StorageUtils

class StorageUtilsTest {

    private val email: String = "ilya-sachuk@mail.ru"

    @Test
    fun checkThatDataSaved() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        StorageUtils.setValue(appContext, StorageUtils.StorageKey.EMAIL, email)

        val value = StorageUtils.getValue(appContext, StorageUtils.StorageKey.EMAIL)

        println(value)
        assert(email.compareTo(value ?: "") == 0)

        //Junit 5 с ним траблы какие-то
        resetStorage(appContext)
    }

    @Test
    fun checkWhenDataWasntAdded() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val value = StorageUtils.getValue(appContext, StorageUtils.StorageKey.EMAIL)

        assert(value == null)

        resetStorage(appContext)
    }

    @Test
    fun checkThatEmailDontSave() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        var notSaved = false;

        try {
            val value = StorageUtils.getCurrentUserEmail(appContext)
        } catch (e: Exception) {
            notSaved = true;
            assert(e is IllegalStateException)
        }

        assert(notSaved)

        resetStorage(appContext)
    }

    @Test
    fun checkThatEmailSaved() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        StorageUtils.setValue(appContext, StorageUtils.StorageKey.EMAIL, email)

        val value = StorageUtils.getCurrentUserEmail(appContext)

        assert(email.compareTo(value) == 0)

        resetStorage(appContext)
    }

    private fun resetStorage(appContext: Context) {
        StorageUtils.setValue(appContext, StorageUtils.StorageKey.EMAIL, null)
    }


}
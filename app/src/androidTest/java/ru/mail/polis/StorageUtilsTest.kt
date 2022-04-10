package ru.mail.polis

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
    }

    @Test
    fun checkWhenDataWasntAdded() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val value = StorageUtils.getValue(appContext, StorageUtils.StorageKey.EMAIL)

        assert(value == null)
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
    }

    @Test
    fun checkThatEmailSaved() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        StorageUtils.setValue(appContext, StorageUtils.StorageKey.EMAIL, email)

        val value = StorageUtils.getCurrentUserEmail(appContext)

        assert(email.compareTo(value) == 0)
    }

//    @AfterEach
//    fun resetStorage() {
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//
//        StorageUtils.setValue(appContext, StorageUtils.StorageKey.EMAIL, null)
//    }


}
package ru.mail.polis.unit

import android.content.Context
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.mail.polis.utils.StorageUtils

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(27))
class StorageUtilsTest: BaseTest() {

    private val email: String = "ilya-sachuk@mail.ru"

    @Test
    fun checkThatDataSaved() {
        StorageUtils.setValue(appContext, StorageUtils.StorageKey.EMAIL, email)

        val value = StorageUtils.getValue(appContext, StorageUtils.StorageKey.EMAIL)

        println(value)
        assert(email.compareTo(value ?: "") == 0)

        //Junit 5 с ним траблы какие-то
        resetStorage(appContext)
    }

    @Test
    fun checkWhenDataWasntAdded() {
        val value = StorageUtils.getValue(appContext, StorageUtils.StorageKey.EMAIL)

        assert(value == null)

        resetStorage(appContext)
    }

    @Test
    fun checkThatEmailDontSave() {
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
        StorageUtils.setValue(appContext, StorageUtils.StorageKey.EMAIL, email)

        val value = StorageUtils.getCurrentUserEmail(appContext)

        assert(email.compareTo(value) == 0)

        resetStorage(appContext)
    }

    private fun resetStorage(appContext: Context) {
        StorageUtils.setValue(appContext, StorageUtils.StorageKey.EMAIL, null)
    }


}
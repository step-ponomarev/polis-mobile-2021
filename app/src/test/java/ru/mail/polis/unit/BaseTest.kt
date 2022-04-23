package ru.mail.polis.unit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import org.junit.Before
import org.robolectric.Robolectric


open class BaseTest {
    protected var appContext: Context = ApplicationProvider.getApplicationContext()

//    val a = FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().targetContext)

    @Before
    fun setUp() {

    }
}
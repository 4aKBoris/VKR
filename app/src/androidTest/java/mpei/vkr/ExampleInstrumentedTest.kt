@file:Suppress("DEPRECATION")

package mpei.vkr

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import mpei.vkr.Constants.LOG_TAG
import mpei.vkr.Crypto.SecretKey
import mpei.vkr.Others.KeyStoreClass
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.security.KeyStore
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("mpei.vkr", appContext.packageName)
    }

    @Test
    fun Test1() {
        val time = Calendar.getInstance()
        time.add(Calendar.DATE, 1)
        Log.d(LOG_TAG, "dwa")
    }
}
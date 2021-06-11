@file:Suppress("DEPRECATION")

package mpei.vkr

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import mpei.vkr.Crypto.SecretKey
import mpei.vkr.Others.KeyStoreClass
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.security.KeyStore

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
        val keyStore = KeyStore.getInstance("JCEKS")
        /*ks.load(null, "wadwadwa".toCharArray())
        val secretKey = SecretKey("AES", 32).generateSecretKeyEncrypt()
        val secretKeyEntry = KeyStore.SecretKeyEntry(secretKey)
        val entryPassword = KeyStore.PasswordProtection("12345678".toCharArray())
        ks.setEntry("wdadwadwa", secretKeyEntry, entryPassword) */
    }
}
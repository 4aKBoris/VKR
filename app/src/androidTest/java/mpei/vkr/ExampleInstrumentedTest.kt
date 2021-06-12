@file:Suppress("DEPRECATION")

package mpei.vkr

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import mpei.vkr.Constants.Certificate
import mpei.vkr.Constants.PrivateKey
import mpei.vkr.Constants.SHA256withRSA
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Crypto.SignatureFile
import mpei.vkr.Others.KeyStoreClass
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

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
        val password = "127485ldaLDA$$$"
        val keyStore = KeyStoreClass(MasterKey(password).getMasterKey())
        val cert = keyStore.getCertificate(Certificate + SHA256withRSA)
        val privateKey = keyStore.getPrivateKeyEntry(PrivateKey + SHA256withRSA)
        Assert.assertArrayEquals(privateKey.certificate.publicKey.encoded, cert.publicKey.encoded)
        val byte = rnd.nextBytes(105895)
        val sign = SignatureFile(SHA256withRSA)
        val signature = sign.getSignature(byte, keyStore)
        assertEquals(sign.verify(byte, cert, signature.first), true)
    }

    companion object {
        private val rnd = Random
    }
}
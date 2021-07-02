@file:Suppress("DEPRECATION")

package mpei.vkr

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mpei.vkr.Constants.path
import mpei.vkr.Others.FileClass
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.security.KeyStore
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
        val file = FileClass()
        GlobalScope.launch {
            val byte = file.readFile(path + "Бабин - Лаборатория хакера.pdf")
        }
    }

    @Test
    fun Test2() {
        val keyStore = KeyStore.getInstance("jks", BouncyCastleProvider())
        keyStore.load(null, "dwadwadwa".toCharArray())
    }

    companion object {
        private val rnd = Random
    }
}
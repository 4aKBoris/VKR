package mpei.vkr

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import mpei.vkr.Others.FileReadWrite
import mpei.vkr.Others.Permissions

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.File
import java.io.FileReader

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
        val k = FileReadWrite("/root/storage/emulated/0/File.txt")
        println(k.readFile().contentToString())
    }
}
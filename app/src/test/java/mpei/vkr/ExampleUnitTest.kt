package mpei.vkr

import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val str = "12345"
        assertEquals("45", str.removePrefix("123"))
    }

    @Test
    fun test1() {
        val str = "0100"
        val k = str.toByteArray(Charsets.UTF_8)
        val s = k.toString(Charsets.UTF_8)
        val t = s.toInt()
        println(t)
    }
}
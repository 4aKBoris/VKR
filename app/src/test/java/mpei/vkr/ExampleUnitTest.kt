package mpei.vkr

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

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
       val byte = rnd.nextBytes(1000000)
        val k = byte.drop(25)
        k.toByteArray()
    }

    @Test
    fun test2() {
        val byte = rnd.nextBytes(1000000)
        val k = byte.copyOfRange(25, byte.size)
    }

    @Test
    fun test3() {
        val byte = rnd.nextBytes(10)
        val k = byte.copyOfRange(5, 10)
        //println(k.joinToString())
    }

    companion object {
        private val rnd = Random
    }
}
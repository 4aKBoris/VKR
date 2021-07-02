package mpei.vkr

import mpei.vkr.Crypto.Algorithms
import mpei.vkr.Crypto.CipherFile
import mpei.vkr.Crypto.SecretKey
import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class CipherFileTest {

    /**
     *testCipher1 - testCipher9 - тестировние функций шифрования и рашифрования на массивах разлицной длины
     */

    @Test
    fun testCipher1() {
        val arr = rnd.nextBytes(4)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test
    fun testCipher2() {
        val arr = rnd.nextBytes(64)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test
    fun testCipher3() {
        val arr = rnd.nextBytes(256)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test
    fun testCipher4() {
        val arr = rnd.nextBytes(1000)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test
    fun testCipher5() {
        val arr = rnd.nextBytes(10000)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test
    fun testCipher6() {
        val arr = rnd.nextBytes(100000)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test
    fun testCipher7() {
        val arr = rnd.nextBytes(1000000)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test
    fun testCipher8() {
        val arr = rnd.nextBytes(10000000)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test
    fun testCipher9() {
        val arr = rnd.nextBytes(100000000)
        list.getCipherAlgorithms().forEach { testAlgorithm(it, arr, list.getKeySizeMax(it)) }
    }

    @Test //Тест различных длин ключей для каждого алгоритма шифрования
    fun testKeySizeAlgorithms() {
        val arr = rnd.nextBytes(128)
        list.getCipherAlgorithms().forEach { testKeySize(it, arr) }
    }

    //Тест функции шифрования и расшифрования для заданного алгоритма и для любой возможной длины ключа
    private fun testKeySize(algorithm: String, arr: ByteArray) {
        for (i in list.getKeySizeMin(algorithm)..list.getKeySizeMax(algorithm) step list.getKeySizeStep(
            algorithm
        )) testAlgorithm(algorithm, arr, i)
    }

    //Тест функции шифрования и расшифрования для заданного алгоритма
    private fun testAlgorithm(algorithm: String, arr: ByteArray, keySize: Int) {
        val sK = SecretKey(algorithm, keySize)
        val secretKey = sK.generateSecretKeyEncrypt()
        val cipher = CipherFile(arr, algorithm, 1, secretKey)
        val pair = cipher.encrypt()
        val cipher2 = CipherFile(pair.first, algorithm, 1, secretKey)
        val clearText = cipher2.decrypt(pair.second)
        Assert.assertArrayEquals(arr, clearText)
    }

    companion object {
        private val rnd = Random
        private val list = Algorithms()
    }
}
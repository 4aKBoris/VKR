package mpei.vkr

import mpei.vkr.Constants.AES
import mpei.vkr.Crypto.Algorithms
import mpei.vkr.Crypto.SecretKey
import org.junit.Assert
import org.junit.Test

class SecretKeyTest {

    @Test //С использованием случайно примеси
    fun test1() {
        list.getHashAlgorithms().forEach {
            testKey(true, it)
        }
    }

    @Test //Без использования случайной примеси
    fun test2() {
        list.getHashAlgorithms().forEach {
            testKey(false, it)
        }
    }

    private fun testKey(flag: Boolean, algorithm: String) {
        val password = "awdwad3kwr8734y78frhsy"
        val sK = SecretKey(AES, 32)
        val key = sK.generateSecretKeyEncrypt(password, algorithm, 1000, flag)
        val key2 = sK.getSecretKeyDecrypt(password, algorithm, 1000, key.second)
        Assert.assertArrayEquals(key.first.encoded, key2.encoded)
    }

    companion object {
        private val list = Algorithms()
    }
}
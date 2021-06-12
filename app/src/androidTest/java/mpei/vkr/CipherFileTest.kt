package mpei.vkr

import androidx.test.ext.junit.runners.AndroidJUnit4
import mpei.vkr.Crypto.CipherFile
import mpei.vkr.Crypto.SecretKey
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class CipherFileTest {
    @Test
    fun test1() {
        val arr = rnd.nextBytes(54875)
        val sK = SecretKey("AES", 32)
        val secretKey = sK.generateSecretKeyEncrypt()
        val cipher = CipherFile(arr, "AES", 2, secretKey)
        val mas = cipher.encrypt()
        val cipherDecrypt = CipherFile(mas.first, "AES", 2, secretKey)
        val arr1 = cipherDecrypt.decrypt(mas.second)
        Assert.assertArrayEquals(arr, arr1)
    }

    companion object {
        private val rnd = Random
    }
}
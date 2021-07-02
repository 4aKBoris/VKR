package mpei.vkr

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import mpei.vkr.Constants.LOG_TAG
import mpei.vkr.Crypto.Algorithms
import mpei.vkr.Crypto.CipherFile
import mpei.vkr.Crypto.SecretKey
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Duration
import java.time.Instant
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class CipherFileAndroidTest {
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

    @Test
    fun cipherSpeed() {
        val n = 1024 * 1024 * 1000
        val m = 1
        val arr = arrayListOf<ByteArray>()
        for (i in 1..m) arr.add(rnd.nextBytes(n))
        list.getCipherAlgorithms().forEach {
            if (it != "Serpent" || it != "GOST3412-2015") {
                val sK = SecretKey(it, list.getKeySizeMax(it))
                val secretKey = sK.generateSecretKeyEncrypt()
                var timeEncrypt: Long = 0
                var timeDecrypt: Long = 0
                arr.forEach { mas ->
                    val encrypt = CipherFile(mas, it, 1, secretKey)
                    val startEncrypt = Instant.now()
                    val k = encrypt.encrypt()
                    val finishEncrypt = Instant.now()
                    timeEncrypt += Duration.between(startEncrypt, finishEncrypt).toMillis()
                    val decrypt = CipherFile(k.first, it, 1, secretKey)
                    val startDecrypt = Instant.now()
                    decrypt.decrypt(k.second)
                    val finishDecrypt = Instant.now()
                    timeDecrypt += Duration.between(startDecrypt, finishDecrypt).toMillis()
                }
                Log.d(LOG_TAG, "${timeEncrypt / m} / ${timeDecrypt / m}                $it")
            }
        }

    }

    companion object {
        private val rnd = Random
        private val list = Algorithms()
    }
}
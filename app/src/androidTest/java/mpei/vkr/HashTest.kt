@file:Suppress("SameParameterValue")

package mpei.vkr

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import mpei.vkr.Constants.LOG_TAG
import mpei.vkr.Crypto.Algorithms
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Test
import org.junit.runner.RunWith
import java.security.MessageDigest
import java.time.Duration
import java.time.Instant
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class HashTest {

    @Test
    fun hashSpeed() {
        val n = 32
        val m = 10
        val nm = 10000
        val arr = arrayListOf<ByteArray>()
        for (i in 1..m) arr.add(rnd.nextBytes(n))
        list.getHashAlgorithms().forEach {
                var time: Long = 0
                arr.forEach { mas ->
                    val start = Instant.now()
                    val hash = messageDigest(mas, it, nm, null)
                    val finish = Instant.now()
                    time += Duration.between(start, finish).toMillis()
                }
                Log.d(LOG_TAG, "${time / 10}        $it")
        }
    }

    companion object {
        private val rnd = Random
        private val list = Algorithms()

        private fun messageDigest(password: ByteArray, hashAlgorithm: String, hashCount: Int, salt: ByteArray?): ByteArray {
            val digest = MessageDigest.getInstance(hashAlgorithm, BouncyCastleProvider())
            for (i in 1..hashCount) {
                digest.update(password)
                if (salt != null) digest.update(salt)
            }
            return digest.digest()
        }
    }
}
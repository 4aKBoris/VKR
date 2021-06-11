@file:Suppress("SameParameterValue")

package mpei.vkr.Crypto

import android.util.Log
import mpei.vkr.Constants.LOG_TAG
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CipherFile(
    private val arr: ByteArray,
    private val cipherAlgorithm: String,
    private val cipherCount: Int,
    private val secretKey: SecretKey
) {

    fun encrypt(): Pair<ByteArray, ByteArray?> {
        val cipherPair = cipherInit(cipherAlgorithm, secretKey)
        return Pair(crypto(arr, cipherPair.first, cipherCount), cipherPair.second)
    }

    fun decrypt(iv: ByteArray?): ByteArray {
        val cipher = cipherInit(cipherAlgorithm, secretKey, iv)
        return crypto(arr, cipher, cipherCount)
    }

    companion object {
        private val secureRandom = SecureRandom()
        private val alg = Algorithms()

        private fun crypto(arr: ByteArray, cipher: Cipher, cipherCount: Int): ByteArray {
            for (i in 1 until cipherCount) cipher.update(arr)
            return cipher.doFinal(arr)
        }

        private fun cipherInit(cipherAlgorithm: String, secretKey: SecretKey): Pair<Cipher, ByteArray?> {
            return if (alg.typeCipher(cipherAlgorithm)) {
                val cipher = Cipher.getInstance(cipherAlgorithm, BouncyCastleProvider())
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom)
                Pair(cipher, null)
            } else {
                val cipher = Cipher.getInstance("$cipherAlgorithm/CBC/PKCS5Padding", BouncyCastleProvider())
                val iv = secureRandom.generateSeed(alg.getIVSize(cipherAlgorithm))
                Log.d(LOG_TAG, alg.getIVSize(cipherAlgorithm).toString())
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv), secureRandom)
                Pair(cipher, iv)
            }
        }

        private fun cipherInit(cipherAlgorithm: String, secretKey: SecretKey, iv: ByteArray?): Cipher {
            return if (alg.typeCipher(cipherAlgorithm)) {
                val cipher = Cipher.getInstance(cipherAlgorithm, BouncyCastleProvider())
                cipher.init(Cipher.DECRYPT_MODE, secretKey, secureRandom)
                cipher
            } else {
                val cipher = Cipher.getInstance("$cipherAlgorithm/CBC/PKCS7Padding", BouncyCastleProvider())
                cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv), secureRandom)
                cipher
            }
        }
    }
}
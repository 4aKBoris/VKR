@file:Suppress("SameParameterValue")

package mpei.vkr.Crypto

import mpei.vkr.Exception.MyException
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

    @Throws(MyException::class)
    fun decrypt(iv: ByteArray?): ByteArray {
        val cipher = cipherInit(cipherAlgorithm, secretKey, iv)
        return crypto(arr, cipher, cipherCount)
    }

    companion object {
        private val secureRandom = SecureRandom()
        private val alg = Algorithms()

        private fun crypto(arr: ByteArray, cipher: Cipher, cipherCount: Int): ByteArray {
            for (i in 1 until cipherCount) cipher.doFinal(arr)
            return cipher.doFinal(arr)
        }

        private fun cipherInit(
            cipherAlgorithm: String,
            secretKey: SecretKey
        ): Pair<Cipher, ByteArray?> {
            val algorithm = if (alg.typeCipher(cipherAlgorithm)) cipherAlgorithm
            else "$cipherAlgorithm/CBC/PKCS7Padding"
            val cipher = Cipher.getInstance(algorithm, BouncyCastleProvider())
            var iv: ByteArray? = null
            if (alg.typeCipher(cipherAlgorithm) && !alg.isHaveIV(cipherAlgorithm)) cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey,
                secureRandom
            )
            else {
                iv = secureRandom.generateSeed(alg.getIVSize(cipherAlgorithm))
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv), secureRandom)
            }
            return Pair(cipher, iv)
        }

        private fun cipherInit(
            cipherAlgorithm: String,
            secretKey: SecretKey,
            iv: ByteArray?
        ): Cipher {
            val algorithm = if (alg.typeCipher(cipherAlgorithm)) cipherAlgorithm
            else "$cipherAlgorithm/CBC/PKCS7Padding"
            val cipher = Cipher.getInstance(algorithm, BouncyCastleProvider())
            if (iv == null) cipher.init(Cipher.DECRYPT_MODE, secretKey, secureRandom)
            else cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv), secureRandom)
            return cipher
        }
    }
}
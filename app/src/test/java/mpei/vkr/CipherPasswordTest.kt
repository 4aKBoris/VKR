package mpei.vkr

import mpei.vkr.Crypto.Algorithms
import mpei.vkr.Crypto.CipherPassword
import mpei.vkr.Crypto.SecretKey
import org.junit.Assert
import org.junit.Test
import java.security.KeyPairGenerator

class CipherPasswordTest: CipherPassword() {

    @Test
    fun testKey() {
        list.getCipherAlgorithms().forEach {
            test(it, list.getKeySizeMin(it))
            test(it, list.getKeySizeMax(it))
        }
    }

    private fun test(cipherAlgorithm: String, keySize: Int) {
        val sK = SecretKey(cipherAlgorithm, keySize)
        val secretKey = sK.generateSecretKeyEncrypt()
        val keyPair = keyPairGenerator.generateKeyPair()
        val key = encrypt(secretKey, keyPair.public)
        val secretKey2 = decrypt(key, keyPair.private, cipherAlgorithm, keySize)
        Assert.assertArrayEquals(secretKey.encoded, secretKey2.encoded)
    }

    companion object {
        private val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        private val list = Algorithms()
    }
}
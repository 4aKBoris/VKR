package mpei.vkr.Crypto

import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

open class CipherPassword {

    protected fun encrypt(secretKey: SecretKey, publicKey: PublicKey): ByteArray {
        val password = secretKey.encoded
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, secureRandom)
        return cipher.doFinal(password)
    }

    protected fun decrypt(key: ByteArray, privateKey: PrivateKey, cipherAlgorithm: String, keySize: Int): SecretKey {
        cipher.init(Cipher.DECRYPT_MODE, privateKey, secureRandom)
        val key1 = cipher.doFinal(key)
        return SecretKeySpec(key1, 0, keySize, cipherAlgorithm)
    }

    companion object {
        private val secureRandom = SecureRandom()
        private val cipher = Cipher.getInstance("RSA")
    }
}
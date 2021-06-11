package mpei.vkr.Crypto

import android.util.Log
import mpei.vkr.Constants.LOG_TAG
import mpei.vkr.Others.KeyStoreClass
import java.security.SecureRandom
import java.security.cert.Certificate
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

open class CipherPassword {

    protected fun encrypt(secretKey: SecretKey, certificate: Certificate): ByteArray {
        val password = secretKey.encoded
        cipher.init(Cipher.ENCRYPT_MODE, certificate, secureRandom)
        return cipher.doFinal(password)
    }

    protected fun decrypt(key: ByteArray, password: String, cipherAlgorithm: String, keySize: Int): SecretKey {
        val keyStore = KeyStoreClass(password)
        val privateKey = keyStore.getPrivateKey()
        cipher.init(Cipher.DECRYPT_MODE, privateKey, secureRandom)
        Log.d(LOG_TAG, keySize.toString())
        val key1 = cipher.doFinal(key)
        Log.d(LOG_TAG, key1.size.toString())
        return SecretKeySpec(key1, 0, keySize, cipherAlgorithm)
    }

    companion object {
        private val secureRandom = SecureRandom()
        private val cipher = Cipher.getInstance("RSA")
        private const val bit = 8
    }
}
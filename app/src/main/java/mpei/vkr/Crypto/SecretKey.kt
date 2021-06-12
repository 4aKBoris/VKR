package mpei.vkr.Crypto

import mpei.vkr.Others.KeyStoreClass
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.cert.Certificate
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class SecretKey(private val cipherAlgorithm: String, private val keySize: Int): CipherPassword() {

    fun generateSecretKeyEncrypt(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(cipherAlgorithm, BouncyCastleProvider())
        keyGenerator.init(keySize * bit, secureRandom)
        return keyGenerator.generateKey()
    }

    fun generateSecretKeyEncrypt(
        password: String,
        hashAlgorithm: String,
        hashCount: Int,
        saltFlag: Boolean
    ): Pair<SecretKey, ByteArray?> {
        val salt = if (saltFlag) secureRandom.generateSeed(16) else null
        val key = messageDigest(password.toByteArray(Charsets.UTF_8), hashAlgorithm, hashCount, salt)
        return Pair(SecretKeySpec(key, 0, keySize, cipherAlgorithm), salt)
    }

    fun getCipherSecretKey(secretKey: SecretKey, certificate: Certificate) = encrypt(secretKey, certificate)

    fun getSecretKeyDecrypt(key: ByteArray, keyStore: KeyStoreClass) = decrypt(key, keyStore, cipherAlgorithm, keySize)

    fun getSecretKeyDecrypt(password: String, hashAlgorithm: String, hashCount: Int, salt: ByteArray?): SecretKey {
        val key = messageDigest(password.toByteArray(Charsets.UTF_8), hashAlgorithm, hashCount, salt)
        return SecretKeySpec(key, 0, keySize * bit, cipherAlgorithm)
    }

    companion object {
        private val secureRandom = SecureRandom()
        private const val bit = 8

        private fun messageDigest(password: ByteArray, hashAlgorithm: String, hashCount: Int, salt: ByteArray?): ByteArray {
            val digest = MessageDigest.getInstance(hashAlgorithm)
            for (i in 1..hashCount) {
                digest.update(password)
                if (salt != null) digest.update(salt)
            }
            return digest.digest()
        }
    }
}
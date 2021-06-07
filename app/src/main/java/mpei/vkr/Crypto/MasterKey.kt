package mpei.vkr.Crypto

import mpei.vkr.Constants.AES
import mpei.vkr.Constants.pathMasterKey
import mpei.vkr.Others.FileReadWrite
import java.io.File
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MasterKey(private val password: String) {

    fun IsCorrect(): Boolean {
        val mas = Decrypt().copyOf(5)
        var flag = true
        for (i in 0..4) if (i + 1 != mas[i].toInt()) flag = false
        return flag
    }

    private fun getMasterPassword() = Decrypt().copyOfRange(5, 37)

    private fun Decrypt(): ByteArray {
        val cipher = Cipher()
        val hash = Hash()
        val file = FileReadWrite(pathMasterKey)
        val arr = file.readFile()
        iv = arr.copyOf(16)
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(hash,"AES"), IvParameterSpec(iv))
        return cipher.doFinal(arr.copyOfRange(16, arr.size))
    }

    fun CipherSecretKey() {
        val cipher = Cipher()
        val hash = Hash()
        secureRandom.nextBytes(iv)
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(hash,"AES"), IvParameterSpec(iv))
        val arr = cipher.doFinal(byteArrayOf(1, 2, 3, 4, 5).plus(generateSecretKey().encoded))
        val file = FileReadWrite(pathMasterKey)
        file.writeFile(iv.plus(arr))
    }

    fun DecryptEncreptMasterKey(pass: String) {
        val masterKey = getMasterPassword()
        File(pathMasterKey).delete()
        CipherSecretKey(masterKey, pass)
    }

    private fun CipherSecretKey(secretKey: ByteArray, pass: String) {
        val cipher = Cipher()
        val hash = Hash(pass)
        secureRandom.nextBytes(iv)
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(hash,"AES"), IvParameterSpec(iv))
        val arr = cipher.doFinal(byteArrayOf(1, 2, 3, 4, 5).plus(secretKey))
        val file = FileReadWrite(pathMasterKey)
        file.writeFile(iv.plus(arr))
    }

    private fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(AES)
        val keyBitSize = 256
        keyGenerator.init(keyBitSize, secureRandom)
        return keyGenerator.generateKey()
    }

    private fun Cipher() = Cipher.getInstance("AES/CBC/PKCS5Padding")

    private fun Hash(): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(password.toByteArray(Charsets.UTF_8))
    }

    private fun Hash(key: String): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(key.toByteArray(Charsets.UTF_8))
    }

    companion object {
        private val secureRandom = SecureRandom()
        private var iv = ByteArray(16)
    }
}
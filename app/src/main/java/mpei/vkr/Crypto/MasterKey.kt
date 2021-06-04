package mpei.vkr.Crypto

import mpei.vkr.Constants.AES
import mpei.vkr.Constants.pathMasterKey
import mpei.vkr.Others.FileReadWrite
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MasterKey(private val password: String) {

    fun IsCorrect(): Boolean {
        val cipher = Cipher()
        val hash = Hash()
        val file = FileReadWrite(pathMasterKey)
        val arr = file.readFile()
        iv = arr.copyOf(16)
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(hash,"AES"), IvParameterSpec(iv))
        val mas = cipher.doFinal(arr.copyOfRange(16, arr.size)).copyOf(5)
        var flag = true
        for (i in 0..4) if (i + 1 != mas[i].toInt()) flag = false
        return flag
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

    companion object {
        private val secureRandom = SecureRandom()
        private var iv = ByteArray(16)
    }
}
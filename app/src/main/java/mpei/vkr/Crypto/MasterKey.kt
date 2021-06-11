package mpei.vkr.Crypto

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mpei.vkr.Constants.AES
import mpei.vkr.Constants.RC4
import mpei.vkr.Constants.SHA256
import mpei.vkr.Constants.pathMasterKey
import mpei.vkr.Others.FileClass
import javax.crypto.SecretKey

class MasterKey(password: String) {

    private val secretKey: SecretKey

    init {
       secretKey = secretKeyRC4.generateSecretKeyEncrypt(password, SHA256, _100, false).first
    }

    suspend fun encryptSecretKey() = withContext(Dispatchers.Default) {
        val masterKey = secretKeyAES.generateSecretKeyEncrypt().encoded
        val cipher = CipherFile(check.plus(masterKey), RC4, _100, secretKey)
        file.writeFile(pathMasterKey, cipher.encrypt().first)
    }

    suspend fun decryptSecretKey(): ByteArray = withContext(Dispatchers.Default) {
        val masterKeyByteArray = file.readFile(pathMasterKey)
        val cipher = CipherFile(masterKeyByteArray, RC4, _100, secretKey)
        val masterKey = cipher.decrypt(null)
        masterKey.drop(8).toByteArray()
    }

    suspend fun changeMasterKey(password: String) {
        val key = decryptSecretKey()
        encryptSecretKey(key, password)
    }

    private suspend fun encryptSecretKey(masterKey: ByteArray, password: String) = withContext(Dispatchers.Default) {
        val sK = secretKeyRC4.generateSecretKeyEncrypt(password, SHA256, _100, false).first
        val cipher = CipherFile(check.plus(masterKey), RC4, _100, sK)
        file.writeFile(pathMasterKey, cipher.encrypt().first)
    }

    @Throws(Exception::class)
    suspend fun isCorrect(): Boolean = withContext(Dispatchers.Default) {
        val arr = file.readFile(pathMasterKey)
        val cipher = CipherFile(arr, RC4, _100, secretKey)
        val checkArray = cipher.decrypt(null).take(8).toByteArray()
        return@withContext checkArray.contentEquals(checkArray)
    }

    companion object {
        private val secretKeyAES = SecretKey(AES, 32)
        private val secretKeyRC4 = SecretKey("RC4", 32)
        private val file = FileClass()
        private const val _100 = 100
        private val check = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    }
}
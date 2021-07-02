@file:Suppress("DEPRECATION")

package mpei.vkr.Crypto

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import mpei.vkr.Constants.*
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileClass
import mpei.vkr.Others.KeyStoreClass
import mpei.vkr.Others.Password
import org.bouncycastle.util.encoders.Base64
import kotlin.random.Random

class Encrypt(
    private val metaData: MetaData,
    private val fileName: String?,
    private val useMasterKey: Boolean,
    private val cipherPasswordFlag: Boolean,
    private val passwordFlag: Boolean,
    private val password1: String?,
    private val password2: String?,
    private val secondPassword: Boolean,
    private val saltFlag: Boolean,
    private val hashAlgorithm: String,
    private val hashCount: Int,
    context: Context,
    password: String,
    private val deleteFile: Boolean,
    private val certificateName: String?
) {
    private val keyStore: KeyStoreClass
    private val sp: SharedPreferences
    private val secretKeyGenerator: SecretKey

    init {
        keyStore = KeyStoreClass(password)
        sp = PreferenceManager.getDefaultSharedPreferences(context)
        secretKeyGenerator = SecretKey(metaData.cipherAlgorithm, metaData.keySize)
    }

    @Throws(MyException::class, Exception::class)
    suspend fun encrypt() {
        val secretKey: javax.crypto.SecretKey
        if (fileName.isNullOrEmpty()) throw MyException("Выберите файл!")
        metaData.clearText = file.readFile(fileName)
        metaData.clearText = byte.plus(metaData.clearText)
        if (useMasterKey) {
            secretKey = secretKeyGenerator.generateSecretKeyEncrypt()
            if (!cipherPasswordFlag) metaData.fileName = cipherPassword(secretKey)
        } else {
            val warning = correct.correctPassword(password1, password2, secondPassword, passwordFlag)
            if (warning != null) throw MyException(warning)
            metaData.hashAlgorithm = hashAlgorithm
            metaData.hashCount = hashCount
            val keyPair = secretKeyGenerator.generateSecretKeyEncrypt(
                password1!!,
                metaData.hashAlgorithm!!,
                metaData.hashCount!!,
                saltFlag
            )
            secretKey = keyPair.first
            metaData.salt = keyPair.second
        }
        val cipher = CipherFile(
            metaData.clearText,
            metaData.cipherAlgorithm,
            metaData.cipherCount,
            secretKey
        )
        val cipherPair = cipher.encrypt()
        metaData.cipherText = cipherPair.first
        metaData.iv = cipherPair.second
        if (metaData.signatureAlgorithm != NotUse) {
            val sign = SignatureFile(metaData.signatureAlgorithm)
            val privateKeyEntry = keyStore.getPrivateKeyEntry(PrivateKey + metaData.signatureAlgorithm)
            val singArray = sign.getSignature(metaData.cipherText, privateKeyEntry.privateKey)
            val certificate = keyStore.getCertificate(Certificate + metaData.signatureAlgorithm)
            metaData.signData = singArray
            metaData.certificate = certificate.encoded
        }
        if (cipherPasswordFlag)
            if (certificateName.isNullOrEmpty() || certificateName == "Выберите сертификат") throw MyException("Выберите сертификат для шифрования пароля!")
            else metaData.cipherPassword = cipherPasswordToFile(secretKey, certificateName)
        file.writeFile(
            "$pathToCipherFiles${file.fileName(fileName)}$Crypto",
            metaData.convertToByteArray()
        )
        if (deleteFile) file.deleteFile(fileName)
    }

    @Throws(Exception::class)
    private fun cipherPassword(secretKey: javax.crypto.SecretKey): String {
        val certificate = keyStore.getCertificate(Certificate + mpei.vkr.Constants.SecretKey)
        val cipherKey = secretKeyGenerator.getCipherSecretKey(secretKey, certificate)
        val cipherKeyString = Base64.toBase64String(cipherKey)
        val alias = Base64.toBase64String(rnd.nextBytes(64))
        sp.edit().apply {
            putString(alias, cipherKeyString)
            apply()
        }
        return alias
    }

    @kotlin.jvm.Throws(Exception::class)
    private fun cipherPasswordToFile(secretKey: javax.crypto.SecretKey, alias: String): ByteArray {
        val certificate = keyStore.getCertificate(alias)
        return secretKeyGenerator.getCipherSecretKey(secretKey, certificate)
    }

    companion object {
        private val byte = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        private val rnd = Random
        private val file = FileClass()
        private val correct = Password()
    }
}
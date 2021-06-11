@file:Suppress("DEPRECATION")

package mpei.vkr.Crypto

import android.content.Context
import kotlin.random.Random
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import mpei.vkr.Constants.*
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileClass
import mpei.vkr.Others.KeyStoreClass
import mpei.vkr.Others.Password
import org.bouncycastle.util.encoders.Base64
import kotlin.jvm.Throws

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
    private val certificateAlias: String?,
    private val hashAlgorithm: String,
    private val hashCount: Int,
    context: Context,
    password: String
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
        if (useMasterKey) {
            secretKey = secretKeyGenerator.generateSecretKeyEncrypt()
            if (!cipherPasswordFlag) metaData.fileName = cipherPassword(secretKey)
        } else {
            correctPassword(password1, password2, secondPassword, passwordFlag)
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
        Log.d(LOG_TAG, metaData.signatureAlgorithm)
        if (metaData.signatureAlgorithm != NotUse) {
            Log.d(LOG_TAG, metaData.signatureAlgorithm)
            val sign = SignatureFile(metaData.signatureAlgorithm)
            val pair = sign.getSignature(metaData.clearText, "12345678")
            metaData.signData = pair.first
            metaData.certificate = pair.second
        }
        if (cipherPasswordFlag) {
            if (certificateAlias.isNullOrBlank() || certificateAlias == "Выберите сертификат") throw MyException("Выберите сертифкат для шифрования пароля!")
            metaData.cipherPassword = cipherPassword(secretKey, certificateAlias)
        }
        file.writeFile(
            "$pathToCipherFiles${file.fileName(fileName)}$Crypto",
            metaData.convertToByteArray()
        )
    }

    private fun cipherPassword(secretKey: javax.crypto.SecretKey): String {
        val certificate = keyStore.getCertificate(Certificate + SHA256withRSA)
        val cipherKey = secretKeyGenerator.getCipherSecretKey(secretKey, certificate)
        val cipherKeyString = Base64.toBase64String(cipherKey)
        val alias = Base64.toBase64String(rnd.nextBytes(64))
        sp.edit().apply {
            putString(alias, cipherKeyString)
            apply()
        }
        return alias
    }

    private fun cipherPassword(secretKey: javax.crypto.SecretKey, alias: String): ByteArray {
        val certificate = keyStore.getCertificate(alias)
        return secretKeyGenerator.getCipherSecretKey(secretKey, certificate)
    }

    private fun correctPassword(p1: String?, p2: String?, sP: Boolean, pF: Boolean) {
        if (p1.isNullOrEmpty()) throw MyException("Введите пароль!")
        if (sP && (p1 != p2)) throw MyException("Введённые пароли не совпадают!")
        if (pF && !correct.isCorrect(p1)) throw MyException("Пароль не соответствует требованиям!")
    }

    companion object {
        private val rnd = Random
        private val file = FileClass()
        private val correct = Password()
    }
}
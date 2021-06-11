@file:Suppress("DEPRECATION")

package mpei.vkr.Crypto

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import mpei.vkr.Constants.Crypto
import mpei.vkr.Constants.NotUse
import mpei.vkr.Constants.pathToClearFiles
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileClass
import mpei.vkr.Others.KeyStoreClass
import org.bouncycastle.util.encoders.Base64
import kotlin.jvm.Throws

class Decrypt(
    private val metaData: MetaData,
    private val certificate: String?,
    password: String,
    private val password1: String?,
    context: Context,
    private val fileName: String?
) {

    private val keyStore: KeyStoreClass
    private val secretKeyClass: SecretKey
    private val sp: SharedPreferences

    init {
        keyStore = KeyStoreClass(password)
        secretKeyClass = SecretKey(metaData.cipherAlgorithm, metaData.keySize)
        sp = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Throws(MyException::class)
    suspend fun decrypt() {
        if (fileName.isNullOrEmpty()) throw MyException("Выберите файл!")
        if (metaData.signatureAlgorithm != NotUse) {
            if (certificate.isNullOrEmpty()) throw MyException("Выберите сертификат!")
            val sign = SignatureFile(metaData.signatureAlgorithm)
            val cert = keyStore.getCertificate(certificate)
            val verify = sign.verify(metaData.cipherText, cert, metaData.signData!!)
            if (!verify) throw MyException("Цифровая подпись не прошла проверку!")
        }
        val secretKey: javax.crypto.SecretKey
        secretKey = if (metaData.cipherPassword != null) secretKeyClass.getSecretKeyDecrypt(metaData.cipherPassword!!)
        else {
            if (metaData.masterKey) {
                val key = sp.getString(metaData.fileName!!, "")!!
                val keyByteArray = Base64.decode(key)
                secretKeyClass.getSecretKeyDecrypt(keyByteArray)
            } else {
                secretKeyClass.getSecretKeyDecrypt(
                    password1!!,
                    metaData.hashAlgorithm!!,
                    metaData.hashCount!!,
                    metaData.salt
                )
            }
        }
        val cipher = CipherFile(metaData.cipherText, metaData.cipherAlgorithm, metaData.cipherCount, secretKey)
        metaData.clearText = cipher.decrypt(metaData.iv)
        file.writeFile("$pathToClearFiles${fileName.removeSuffix(Crypto)}", metaData.clearText)
    }

    companion object {
        private val file = FileClass()
    }
}
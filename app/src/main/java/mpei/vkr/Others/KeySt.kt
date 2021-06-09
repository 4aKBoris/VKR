package mpei.vkr.Others

import mpei.vkr.Constants.Certificate
import mpei.vkr.Constants.KEY_STORE_ALGORITHM
import mpei.vkr.Constants.PATH_KEY_STORE
import mpei.vkr.Constants.pathToCertificate
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.KeyStore
import java.security.cert.Certificate

class KeySt(password: String) {

    private val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)

    init {
        val keyStoreData = FileInputStream(PATH_KEY_STORE)
        keyStore.load(keyStoreData, password.toCharArray())
    }

    fun algorithms() = keyStore.aliases().toList().filter {
        it.contains(Certificate)
    }.map { it.removePrefix(Certificate) }.toTypedArray()

    fun getCertificate(alias: String) = keyStore.getCertificate(alias)!!

    fun saveCertificate(name: String, certificate: Certificate) {
        val certificateOutputStream = FileOutputStream(pathToCertificate + name)
        certificateOutputStream.write(certificate.encoded)
        certificateOutputStream.close()
    }

}
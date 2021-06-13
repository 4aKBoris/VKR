package mpei.vkr.Others

import mpei.vkr.Constants.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.Certificate
import java.security.cert.CertificateFactory


class KeyStoreClass(password: String) {

    private val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
    private val password: CharArray

    init {
        val keyStoreData = FileInputStream(PATH_KEY_STORE)
        this.password = password.toCharArray()
        keyStore.load(keyStoreData, this.password)
    }

    fun algorithms() = keyStore.aliases().toList().filter {
        it.contains(Certificate)
    }.map { it.removePrefix(Certificate) }.toTypedArray()

    fun certificateNames() =
        keyStore.aliases().toList().filterNot { it.contains(PrivateKey) }.toTypedArray()

    fun getCertificate(alias: String): Certificate = keyStore.getCertificate(alias)!!

    fun getPrivateKeyEntry(alias: String): KeyStore.PrivateKeyEntry {
        val entryPassword: KeyStore.ProtectionParameter =
            KeyStore.PasswordProtection(alias.toCharArray())
        return keyStore.getEntry(alias, entryPassword) as KeyStore.PrivateKeyEntry
    }

    fun getPrivateKey() = getPrivateKeyEntry(PrivateKey + SecretKey).privateKey!!

    fun saveCertificate(name: String, certificate: Certificate) {
        val certificateOutputStream = FileOutputStream(pathToCertificate + name)
        certificateOutputStream.write(certificate.encoded)
        certificateOutputStream.close()
    }

    fun isHaveCertificates() =
        keyStore.aliases().toList().find { it.contains(Certificate) }.isNullOrEmpty()

    fun saveKeyStore() {
        val keyStoreOutputStream = FileOutputStream(PATH_KEY_STORE)
        keyStore.store(keyStoreOutputStream, password)
    }

    fun importCertificate(pathCertificate: String) {
        val certificateFactory = CertificateFactory.getInstance(X509)
        val certificateInputStream = FileInputStream(pathCertificate)
        val certificate = certificateFactory.generateCertificate(certificateInputStream)
        keyStore.setCertificateEntry(File(pathCertificate).name, certificate)
        saveKeyStore()
    }

    fun setPrivateKey(privateKey: PrivateKey, alias: String, certificate: Certificate) {
        keyStore.setKeyEntry(
            alias,
            privateKey,
            alias.toCharArray(),
            arrayOf(certificate)
        )
    }

    fun setCertificate(certificate: Certificate, alias: String) {
        keyStore.setCertificateEntry(alias, certificate)
    }
}
package mpei.vkr.Crypto

import mpei.vkr.Constants.Certificate
import mpei.vkr.Others.KeyStoreClass
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.ByteArrayInputStream
import java.security.SecureRandom
import java.security.Signature
import java.security.cert.Certificate
import java.security.cert.CertificateFactory

class SignatureFile(private val signatureAlgorithm: String) {

    fun getSignature(arr: ByteArray, password: String): Pair<ByteArray, ByteArray> {
        val keyStore = KeyStoreClass(password)
        val privateKeyEntry = keyStore.getPrivateKeyEntry(signatureAlgorithm)
        val signature = Signature.getInstance(signatureAlgorithm, BouncyCastleProvider())
        signature.initSign(privateKeyEntry.privateKey, secureRandom)
        signature.update(arr)
        return Pair(signature.sign(), keyStore.getCertificate(Certificate + signatureAlgorithm).encoded)
    }

    fun verify(arr: ByteArray, certificate: Certificate, signData: ByteArray): Boolean {
        val signature = Signature.getInstance(signatureAlgorithm, BouncyCastleProvider())
        signature.initVerify(certificate)
        signature.update(arr)
        return signature.verify(signData)
    }

    companion object {
        private val secureRandom = SecureRandom()
    }
}
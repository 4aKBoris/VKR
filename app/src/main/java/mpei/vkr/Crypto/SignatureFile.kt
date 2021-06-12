package mpei.vkr.Crypto

import mpei.vkr.Constants.Certificate
import mpei.vkr.Constants.PrivateKey
import mpei.vkr.Others.KeyStoreClass
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import java.security.Signature
import java.security.cert.Certificate

class SignatureFile(private val signatureAlgorithm: String) {

    fun getSignature(arr: ByteArray, keyStore: KeyStoreClass): Pair<ByteArray, ByteArray> {
        val privateKeyEntry = keyStore.getPrivateKeyEntry(PrivateKey + signatureAlgorithm)
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
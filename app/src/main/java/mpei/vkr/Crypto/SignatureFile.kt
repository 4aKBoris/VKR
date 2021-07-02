package mpei.vkr.Crypto

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.Signature

class SignatureFile(private val signatureAlgorithm: String) {

    @Throws(Exception::class)
    fun getSignature(arr: ByteArray, privateKey: PrivateKey): ByteArray {
        val signature = Signature.getInstance(signatureAlgorithm, BouncyCastleProvider())
        signature.initSign(privateKey, secureRandom)
        signature.update(arr)
        return signature.sign()
    }

    fun verify(arr: ByteArray, publicKey: PublicKey, signData: ByteArray): Boolean {
        val signature = Signature.getInstance(signatureAlgorithm, BouncyCastleProvider())
        signature.initVerify(publicKey)
        signature.update(arr)
        return signature.verify(signData)
    }

    companion object {
        private val secureRandom = SecureRandom()
    }
}
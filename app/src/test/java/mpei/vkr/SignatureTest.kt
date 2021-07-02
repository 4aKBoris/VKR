package mpei.vkr

import mpei.vkr.Crypto.SignatureFile
import org.junit.Assert
import org.junit.Test
import java.security.KeyPairGenerator
import kotlin.random.Random

class SignatureTest {

    @Test
    fun testMD5withRSA() {
        val sign = SignatureFile("MD5withRSA")
        val keyPair = keyPairGeneratorRSA.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA1withRSA() {
        val sign = SignatureFile("SHA1withRSA")
        val keyPair = keyPairGeneratorRSA.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA224withRSA() {
        val sign = SignatureFile("SHA224withRSA")
        val keyPair = keyPairGeneratorRSA.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA256withRSA() {
        val sign = SignatureFile("SHA256withRSA")
        val keyPair = keyPairGeneratorRSA.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA384withRSA() {
        val sign = SignatureFile("SHA384withRSA")
        val keyPair = keyPairGeneratorRSA.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA512withRSA() {
        val sign = SignatureFile("SHA512withRSA")
        val keyPair = keyPairGeneratorRSA.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA256withDSA() {
        val sign = SignatureFile("SHA256withDSA")
        val keyPair = keyPairGeneratorDSA.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA1withECDSA() {
        val sign = SignatureFile("SHA1withECDSA")
        val keyPair = keyPairGeneratorEC.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA224withECDSA() {
        val sign = SignatureFile("SHA224withECDSA")
        val keyPair = keyPairGeneratorEC.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA256withECDSA() {
        val sign = SignatureFile("SHA256withECDSA")
        val keyPair = keyPairGeneratorEC.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA384withECDSA() {
        val sign = SignatureFile("SHA384withECDSA")
        val keyPair = keyPairGeneratorEC.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    @Test
    fun testSHA512withECDSA() {
        val sign = SignatureFile("SHA512withECDSA")
        val keyPair = keyPairGeneratorEC.generateKeyPair()
        val signArray = sign.getSignature(arr, keyPair.private)
        Assert.assertEquals(sign.verify(arr, keyPair.public, signArray), true)
    }

    companion object {
        private val rnd = Random
        private val keyPairGeneratorRSA = KeyPairGenerator.getInstance("RSA")
        private val keyPairGeneratorDSA = KeyPairGenerator.getInstance("DSA")
        private val keyPairGeneratorEC = KeyPairGenerator.getInstance("EC")
        private val arr = rnd.nextBytes(100000)
    }
}
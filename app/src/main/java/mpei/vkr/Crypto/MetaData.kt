package mpei.vkr.Crypto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mpei.vkr.Constants.NotUse
import mpei.vkr.Exception.MyException

@JsonPropertyOrder(
    "cAlg",
    "cCount",
    "keySize",
    "masterKey",
    "sAlg",
    "hAlg",
    "hCount",
    "salt",
    "iv",
    "fileName",
    "certificate",
    "signData",
    "cipherPassword"
)
class MetaData() {

    @JsonIgnore
    lateinit var clearText: ByteArray

    @JsonIgnore
    lateinit var cipherText: ByteArray

    @JsonProperty("cAlg")
    var cipherAlgorithm = "AES"

    @JsonProperty("cCount")
    var cipherCount = 1

    @JsonProperty("keySize")
    var keySize = 32

    @JsonProperty("masterKey")
    var masterKey = true

    @JsonProperty("sAlg")
    var signatureAlgorithm = NotUse

    @JsonProperty("hAlg")
    var hashAlgorithm: String? = null

    @JsonProperty("hCount")
    var hashCount: Int? = null

    @JsonProperty("salt")
    var salt: ByteArray? = null

    @JsonProperty("iv")
    var iv: ByteArray? = null

    @JsonProperty("fileName")
    var fileName: String? = null

    @JsonProperty("certificate")
    var certificate: ByteArray? = null

    @JsonProperty("signData")
    var signData: ByteArray? = null

    @JsonProperty("cipherPassword")
    var cipherPassword: ByteArray? = null

    @JsonIgnore
    var cipherFile: ByteArray? = null

    @JsonIgnore
    @Throws(MyException::class)
    constructor(_cipherFile: ByteArray) : this() {
        cipherFile = _cipherFile
    }

    @JsonIgnore
    @kotlin.jvm.Throws(MyException::class)
    suspend fun getMetaData(): MetaData = withContext(Dispatchers.Default) {
        val nameSize = cipherFile!!.copyOf(7)
        val name = nameSize.copyOf(3)
        val size = nameSize.copyOfRange(3, 7).toString(Charsets.UTF_8).toInt()
        if (!name.contentEquals(VKR)) throw MyException("Данный фал не был зашифрован в данном приложении, и расшифровать его не получится!")
        val json = cipherFile!!.copyOfRange(7, 7 + size)
        val jsonObject = mapper.readValue(json, MetaData::class.java)
        jsonObject.cipherText = cipherFile!!.copyOfRange(7 + size, cipherFile!!.size)
        return@withContext jsonObject
    }

    @JsonIgnore
    fun convertToByteArray(): ByteArray {
        val json = mapper.writeValueAsBytes(this)
        var length = json.size.toString()
        while (length.length < 4) length = "0$length"
        return VKR.plus(length.toByteArray(Charsets.UTF_8)).plus(json).plus(cipherText)
    }

    constructor(
        _cipherAlgorithm: String,
        _cipherCount: Int,
        _keySize: Int,
        _masterKey: Boolean,
        _signatureAlgorithm: String,
    ) : this() {
        cipherAlgorithm = _cipherAlgorithm
        cipherCount = _cipherCount
        keySize = _keySize
        masterKey = _masterKey
        signatureAlgorithm = _signatureAlgorithm
    }

    companion object {
        private val VKR = byteArrayOf(86, 75, 82)
        var mapper = ObjectMapper()
    }
}
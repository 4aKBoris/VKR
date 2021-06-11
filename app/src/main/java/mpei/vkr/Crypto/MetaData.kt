package mpei.vkr.Crypto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
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
    fun getMetaData(): MetaData {
        val name = cipherFile!!.take(3)
        if (name != VKR) throw MyException("Данный фал не был зашифрован в данном приложении, и расшифровать его не получится!")
        var array = cipherFile!!.drop(3)
        val size = array.take(6).toByteArray()
        val jsonSize = size.toString(Charsets.UTF_8).toInt()
        array = array.drop(6)
        val json = array.take(jsonSize).toByteArray()
        val jsonClass = mapper.readValue(json, MetaData::class.java)
        jsonClass.cipherText = array.drop(jsonSize).toByteArray()
        return jsonClass
    }

    @JsonIgnore
    fun convertToByteArray(): ByteArray {
        val json = mapper.writeValueAsBytes(this)
        var length = json.size.toString()
        while (length.length < 6) length = "0$length"
        return VKR.toByteArray().plus(length.toByteArray(Charsets.UTF_8)).plus(json).plus(cipherText)
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
        private val VKR = listOf<Byte>(86, 75, 82)
        var mapper = ObjectMapper()
    }
}
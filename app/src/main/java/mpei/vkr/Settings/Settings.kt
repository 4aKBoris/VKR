@file:Suppress("PackageName")

package mpei.vkr.Settings

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import mpei.vkr.Constants.AES
import mpei.vkr.Constants.SHA256

@JsonPropertyOrder()
class Settings(): JSONConvert() {
    var hashAlgorithm = SHA256
    var hashCount = 1
    var cipherAlgorithm = AES
    var cipherCount = 1
    var BCM = "CBC"
    var padding = "PKCS5Padding"
    var salt = false
    var secondPassword = true
    var deleteFile = false
    var keySize = 32
    var passwordFlag = true
    var signature = "Не использовать"
    var сipherPassword = false
    var numberPage = 0
}

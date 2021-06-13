@file:Suppress("PackageName")

package mpei.vkr.Constants

import android.annotation.SuppressLint

internal const val ARG_MASTER_KEY = "MASTER_KEY"
@SuppressLint("SdCardPath")
internal const val pathMasterKey = "/data/data/mpei.vkr/MasterKey.pub"
@SuppressLint("SdCardPath")
internal const val PATH_KEY_STORE = "/data/data/mpei.vkr/KeyStore.ks"
internal const val KEY_STORE_ALGORITHM = "PKCS12"
internal const val AES = "AES"
internal const val RC4 = "RC4"
internal const val X509 = "X.509"
internal const val SHA512 = "SHA512"
internal const val PrivateKey = "PrivateKey"
internal const val Crypto = ".crypto"
internal const val SecretKey = "SecretKey"
internal const val UseMasterKey = "UseMasterKey"
internal const val SHA256 = "SHA-256"
internal const val path = "/storage/emulated/0/"
internal const val LOG_TAG = "LOG_TAG"
internal const val pathToCertificate = "/storage/emulated/0/VKR/Certificates/"
internal const val pathToCipherFiles = "/storage/emulated/0/VKR/CipherFiles/"
internal const val pathToClearFiles = "/storage/emulated/0/VKR/ClearFiles/"
internal const val CountHours = "CountHours"
internal const val CountTrying = "CountTrying"
internal const val MasterKey = "MasterKey"
internal const val FileName = "FileName"

internal const val useLittleLetter = "useLittleLetter"
internal const val useBigLetter = "useBigLetter"
internal const val useSpecialSymbols = "useSpecialSymbols"
internal const val LENGTH = "length"

internal const val Salt = "Salt"
internal const val SecondPassword = "SecondPassword"
internal const val DeleteFile = "DeleteFile"
internal const val PasswordFlag = "PasswordFlag"
internal const val CipherPassword = "CipherPassword"

internal const val CipherAlgorithm = "CipherAlgorithm"
internal const val CipherCount = "CipherCount"
internal const val KeySize = "KeySize"

internal const val HashAlgorithm = "HashAlgorithm"
internal const val HashCount = "HashCount"

internal const val SignatureAlgorithm = "SignatureAlgorithm"
internal const val SHA256withRSA = "SHA256withRSA"

internal const val Certificate = "Certificate"
internal const val NotUse = "Не использовать"


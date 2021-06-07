@file:Suppress("PackageName")

package mpei.vkr.Constants

import android.annotation.SuppressLint
import android.os.Environment
import mpei.vkr.Crypto.MasterKey

internal const val ARG_MASTER_KEY = "MASTER_KEY"
@SuppressLint("SdCardPath")
internal const val pathMasterKey = "/data/data/mpei.vkr/MasterKey.pub"
internal const val AES = "AES"
internal const val SHA256 = "SHA-256"
internal const val path = "/storage/emulated/0/"
internal const val LOG_TAG = "LOG_TAG"
internal const val ARG_POSITION = "ARG_POSITION"

internal const val useLittleLetter = "useLittleLetter"
internal const val useBigLetter = "useBigLetter"
internal const val useSpecialSymbols = "useSpecialSymbols"
internal const val LENGTH = "length"

internal const val Salt = "Salt"
internal const val SecondPassword = "SecondPassword"
internal const val DeleteFile = "DeleteFile"
internal const val PasswordFlag = "PasswordFlag"
internal const val CipherPassword = "CipherPassword"

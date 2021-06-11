package mpei.vkr.Others

import android.content.SharedPreferences
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.Regex.RegexExpr
import kotlin.jvm.Throws

class Password(private val flag: Boolean) {

    private val regex = RegexExpr()

    fun isCorrect(password: String) =
        (!regex.regLittle.containsMatchIn(password) || !regex.regBig.containsMatchIn(password) || !regex.regSpecial.containsMatchIn(
            password
        ))
}
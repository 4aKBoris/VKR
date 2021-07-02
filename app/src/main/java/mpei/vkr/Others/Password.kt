package mpei.vkr.Others

import mpei.vkr.Exception.MyException
import mpei.vkr.Regex.RegexExpr

class Password {

    fun correctPassword(p1: String?, p2: String?, sP: Boolean, pF: Boolean): String? {
        if (p1.isNullOrEmpty()) return "Введите пароль!"
        if (sP && (p1 != p2)) return "Введённые пароли не совпадают!"
        if (pF && isCorrect(p1)) return "Пароль не соответствует требованиям!"
        return null
    }

    private fun isCorrect(password: String) =
        (!contains(reg.regLittle, password) || !contains(
            reg.regBig,
            password
        ) || !contains(reg.regSpecial, password))

    fun isCorrectMasterKey(
        password1: String?,
        password2: String?,
        little: Boolean,
        big: Boolean,
        special: Boolean,
        length: Int
    ): String? {
        if (password1 != password2) return "Введённые пароли не совпадают!"
        if (password1.isNullOrEmpty()) return "Введите мастер-пароль!"
        if (little && !contains(reg.regLittle, password1)) return "В мастер-пароле отсутствуют строчные буквы!"
        if (big && !contains(reg.regBig, password1)) return "В мастер-пароле отсутствуют заглавные буквы!"
        if (special && !contains(reg.regSpecial, password1)) return "В мастер-пароле отсутствуют специальные символы!"
        if (password1.length < length) return "Длина мастер-пароля меньше необходимой!"
        return null
    }

    @Throws(MyException::class)
    fun isCorrectMasterKey(
        password1: String?,
        password2: String?,
        masterKey: String,
        little: Boolean,
        big: Boolean,
        special: Boolean,
        length: Int
    ): String? {
        if (masterKey == password1) return "Новый мастер-пароль не должен совпадать со старым!"
        isCorrectMasterKey(password1, password2, little, big, special, length)
        return null
    }

    companion object {
        private val reg = RegexExpr()
        private fun contains(regex: Regex, password: String) = regex.containsMatchIn(password)
    }
}
package mpei.vkr.Others

import mpei.vkr.Exception.MyException
import mpei.vkr.Regex.RegexExpr

class Password {

    fun isCorrect(password: String) =
        (!contains(reg.regLittle, password) || !contains(
            reg.regBig,
            password
        ) || !contains(reg.regSpecial, password))

    @Throws(MyException::class)
    fun isCorrectMasterKey(
        password1: String?,
        password2: String?,
        little: Boolean,
        big: Boolean,
        special: Boolean,
        length: Int
    ) {
        if (password1 != password2) throw MyException("Введённые пароли не совпадают!")
        if (password1.isNullOrEmpty()) throw MyException("Введите мастер ключ!")
        if (little && !contains(reg.regLittle, password1)) throw MyException("В мастер ключе отсутствуют строчные буквы!")
        if (big && !contains(reg.regBig, password1)) throw MyException("В мастер ключе отсутствуют заглавные буквы!")
        if (special && !contains(reg.regSpecial, password1)) throw MyException("В мастер ключе отсутствуют специальные символы!")
        if (password1.length < length) throw MyException("Длина мастер ключа меньше необходимой!")
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
    ) {
        if (masterKey == password1) throw MyException("Новый мастер ключ не должен совпадать со старым!")
        isCorrectMasterKey(password1, password2, little, big, special, length)
    }

    companion object {
        private val reg = RegexExpr()
        private fun contains(regex: Regex, password: String) = regex.containsMatchIn(password)
    }

}
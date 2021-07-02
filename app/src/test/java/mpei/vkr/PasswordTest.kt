package mpei.vkr

import mpei.vkr.Others.Password
import org.junit.Assert
import org.junit.Test

class PasswordTest {

    @Test
    fun passwordTest() {
        var p1: String? = null
        var flag = correct.correctPassword(p1, null, sP = false, pF = false)
        Assert.assertEquals(flag, "Введите пароль!")
        flag = correct.correctPassword(p1, null, sP = false, pF = true)
        Assert.assertEquals(flag, "Введите пароль!")
        flag = correct.correctPassword(p1, null, sP = true, pF = false)
        Assert.assertEquals(flag, "Введите пароль!")
        flag = correct.correctPassword(p1, null, sP = true, pF = true)
        Assert.assertEquals(flag, "Введите пароль!")

        p1= "123456"
        flag = correct.correctPassword(p1, null, sP = false, pF = false)
        Assert.assertEquals(flag, null)
        flag = correct.correctPassword(p1, null, sP = false, pF = true)
        Assert.assertEquals(flag, "Пароль не соответствует требованиям!")

        p1= "123456qwerty"
        flag = correct.correctPassword(p1, null, sP = false, pF = false)
        Assert.assertEquals(flag, null)
        flag = correct.correctPassword(p1, null, sP = false, pF = true)
        Assert.assertEquals(flag, "Пароль не соответствует требованиям!")

        p1= "123456qwertyQWERTY"
        flag = correct.correctPassword(p1, null, sP = false, pF = false)
        Assert.assertEquals(flag, null)
        flag = correct.correctPassword(p1, null, sP = false, pF = true)
        Assert.assertEquals(flag, "Пароль не соответствует требованиям!")

        p1= "123456qwertyQWERTY!"
        flag = correct.correctPassword(p1, null, sP = false, pF = false)
        Assert.assertEquals(flag, null)
        flag = correct.correctPassword(p1, null, sP = false, pF = true)
        Assert.assertEquals(flag, null)

        p1 = "123456"
        var p2 = "123456"
        flag = correct.correctPassword(p1, p2, sP = true, pF = false)
        Assert.assertEquals(flag, null)

        p1 = "123456"
        p2 = "1234567"
        flag = correct.correctPassword(p1, p2, sP = true, pF = false)
        Assert.assertEquals(flag, "Введённые пароли не совпадают!")
    }

    @Test
    fun masterKeyTest() {
        var p1: String? = "123456"
        val masterKey = "123456"
        var flag = correct.isCorrectMasterKey(p1, null, masterKey,
            little = false,
            big = false,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, "Новый мастер-пароль не должен совпадать со старым!")
        p1 = "1234"
        flag = correct.isCorrectMasterKey(p1, null, masterKey,
            little = false,
            big = false,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, null)

        p1 = "123456"
        var p2: String?  = "123456"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = false,
            big = false,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, null)

        p2 = "1234"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = false,
            big = false,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, "Введённые пароли не совпадают!")

        p1 = null
        p2 = null
        flag = correct.isCorrectMasterKey(p1, p2,
            little = false,
            big = false,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, "Введите мастер-пароль!")

        p1 = "123456"
        p2 = "123456"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = true,
            big = false,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, "В мастер-пароле отсутствуют строчные буквы!")

        p1 = "123456qwerty"
        p2 = "123456qwerty"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = true,
            big = false,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, null)

        p1 = "123456qwerty"
        p2 = "123456qwerty"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = true,
            big = true,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, "В мастер-пароле отсутствуют заглавные буквы!")

        p1 = "123456qwertyQWERTY"
        p2 = "123456qwertyQWERTY"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = true,
            big = true,
            special = false,
            length = 1
        )
        Assert.assertEquals(flag, null)

        p1 = "123456qwertyQWERTY"
        p2 = "123456qwertyQWERTY"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = true,
            big = true,
            special = true,
            length = 1
        )
        Assert.assertEquals(flag, "В мастер-пароле отсутствуют специальные символы!")

        p1 = "123456qwertyQWERTY!"
        p2 = "123456qwertyQWERTY!"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = true,
            big = true,
            special = true,
            length = 1
        )
        Assert.assertEquals(flag, null)

        p1 = "123456qwertyQWERTY!"
        p2 = "123456qwertyQWERTY!"
        flag = correct.isCorrectMasterKey(p1, p2,
            little = true,
            big = true,
            special = true,
            length = 20
        )
        Assert.assertEquals(flag, "Длина мастер-пароля меньше необходимой!")
    }

    companion object {
        private val correct = Password()
    }
}
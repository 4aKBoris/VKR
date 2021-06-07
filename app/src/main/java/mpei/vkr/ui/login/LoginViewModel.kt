@file:Suppress("UNREACHABLE_CODE")

package mpei.vkr.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.MainActivity
import mpei.vkr.Regex.RegexExpr
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.Throws

class LoginViewModel : ViewModel(), CoroutineScope {
    private val _password1 = MutableLiveData("")
    private val _password2 = MutableLiveData("")
    private val _visibility = MutableLiveData(false)
    private val _warning = MutableLiveData(false)
    private val _buttonEnabled = MutableLiveData(true)

    val password1: MutableLiveData<String> = _password1
    val password2: MutableLiveData<String> = _password2
    val visibility: MutableLiveData<Boolean> = _visibility
    val warning: LiveData<Boolean> = _warning
    val buttonEnabled: LiveData<Boolean> = _buttonEnabled

    fun buttonConfirm(view: View) = launch(Dispatchers.Main) {
        _warning.value = false
        _buttonEnabled.value = false
        try {
            isCorrect(_password1.value, _password2.value)
            val intent = Intent(view.context, MainActivity::class.java)
            intent.putExtra(ARG_MASTER_KEY, _password1.value)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(view.context, intent, null)
        } catch (e: MyException) {
            warningFragment.Visible(e.message!!)
            _warning.value = true
            delay(3000L)
            _warning.value = false
        } finally {
            _buttonEnabled.value = true
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    companion object {

        private val job = SupervisorJob()

        @SuppressLint("StaticFieldLeak")
        private val warningFragment = WarningFragment()
        private val regex = RegexExpr()

        @Throws(MyException::class)
        private fun isCorrect(pass1: String?, pass2: String?) {
            if ((pass1 == null || pass2 == null) || (pass1.isEmpty() || pass2.isEmpty())) throw MyException(
                "Введите мастер ключ!"
            )
            if (pass1 != pass2) throw MyException("Введённые мастер ключи не совпадают!")
            if (!regex.regLittle.containsMatchIn(pass1) || !regex.regBig.containsMatchIn(
                    pass1
                ) || !regex.regSpecial.containsMatchIn(pass1)
            ) throw MyException("Мастер ключ не соответствует требованиям!")
            val mk = MasterKey(pass1)
            mk.CipherSecretKey()
        }
    }
}
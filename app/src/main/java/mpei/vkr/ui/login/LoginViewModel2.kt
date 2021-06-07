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

class LoginViewModel2 : ViewModel(), CoroutineScope {
    private val _password = MutableLiveData("")
    private val _visibility = MutableLiveData(false)
    private val _warning = MutableLiveData(false)
    private val _buttonEnabled = MutableLiveData(true)

    val password: MutableLiveData<String> = _password
    val visibility: MutableLiveData<Boolean> = _visibility
    val warning: LiveData<Boolean> = _warning
    val buttonEnabled: LiveData<Boolean> = _buttonEnabled

    fun buttonConfirm(view: View) = launch(Dispatchers.Main) {
        _warning.value = false
        try {
            isCorrect(_password.value)
            val intent = Intent(view.context, MainActivity::class.java)
            intent.putExtra(ARG_MASTER_KEY, _password.value)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(view.context, intent, null)
        } catch (e: MyException) {
            warningFragment.Visible(e.message!!)
            changeVisible()
        } catch (e: Exception) {
            warningFragment.Visible("Мастер ключ введён неверно!")
            changeVisible()
        }
    }

    private suspend fun changeVisible() {
        _warning.value = true
        delay(3000L)
        _warning.value = false
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    companion object {

        private val job = SupervisorJob()

        @SuppressLint("StaticFieldLeak")
        private val warningFragment = WarningFragment()

        @Throws(MyException::class)
        private fun isCorrect(pass: String?) {
            if (pass == null) throw MyException("Введите мастер ключ!")
            val mk = MasterKey(pass)
            if (pass.isEmpty()) throw MyException("Введите мастер ключ!")
            if (!mk.IsCorrect()) throw MyException("Мастер ключ введён неверно!")
        }
    }
}
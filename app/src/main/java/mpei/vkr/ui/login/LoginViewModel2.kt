@file:Suppress("DEPRECATION")

package mpei.vkr.ui.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import kotlinx.coroutines.*
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.CountHours
import mpei.vkr.Constants.CountTrying
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.MainActivity
import mpei.vkr.Others.ToastShow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class LoginViewModel2(application: Application) : AndroidViewModel(application), CoroutineScope,
    LifecycleObserver {
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val sp = PreferenceManager.getDefaultSharedPreferences(context)
    private val _password = MutableLiveData("")
    private val _visibility = MutableLiveData(false)
    private val _warning = MutableLiveData(false)
    private val _buttonEnabled = MutableLiveData(true)
    private val _textEditEnabled = MutableLiveData(true)
    private val _buttonVisibilityEnabled = MutableLiveData(true)
    private var countTrying = sp.getInt(CountTrying, 3)
    private val trying = sp.getBoolean(Trying, true)

    val password: MutableLiveData<String> = _password
    val visibility: MutableLiveData<Boolean> = _visibility
    val warning: LiveData<Boolean> = _warning
    val buttonEnabled: LiveData<Boolean> = _buttonEnabled
    val textEditEnabled: LiveData<Boolean> = _textEditEnabled
    val buttonVisibilityEnabled: LiveData<Boolean> = _buttonVisibilityEnabled

    fun buttonConfirm(view: View) = launch(Dispatchers.Main) {
        _warning.value = false
        try {
            val masterKey = MasterKey(_password.value!!)
            if (!masterKey.isCorrect()) throw MyException(incorrect)
            val intent = Intent(view.context, MainActivity::class.java)
            intent.putExtra(ARG_MASTER_KEY, _password.value)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(view.context, intent, null)
        } catch (e: MyException) {
            exception(e.message!!)
        } catch (e: Exception) {
            exception(incorrect)
        } finally {
            delay(3000L)
            change(true)
        }
    }

    private suspend fun exception(message: String) {
        change(false)
        warningFragment.visible(message)
        toast.show(context, "Осталось ${textTrying()} ввода мастер-пароля!")
        if (countTrying == 0) {
            toast.show(context, "Приложение блокируется на 1 день!")
            sp.edit().apply {
                val time = Calendar.getInstance()
                val countHours = sp.getInt(CountHours, 24)
                time.add(Calendar.HOUR, countHours)
                putLong(TimeTrying, time.timeInMillis)
                putBoolean(Trying, false)
                apply()
            }
            delay(3000L)
            exitProcess(0)
        }
    }

    private fun change(flag: Boolean) {
        _warning.value = !flag
        _buttonEnabled.value = flag
    }

    @SuppressLint("CommitPrefEdits")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun checkTrying() = launch {
        if (!trying) {
            val time = Calendar.getInstance()
            time.timeInMillis = sp.getLong(TimeTrying, 0)
            val timeNow = Calendar.getInstance()
            if (timeNow.before(time)) {
                val info = "Приложение заблокировано до ${formatter.format(time.time)}"
                toast.show(context, info)
                _warning.value = true
                _buttonEnabled.value = false
                _textEditEnabled.value = false
                _buttonVisibilityEnabled.value = false
                warningFragment.visible(info)
                delay(10000L)
                exitProcess(0)
            } else sp.edit().apply {
                putBoolean(Trying, true)
                apply()
            }
        }
    }

    private fun textTrying() = when (--countTrying) {
        1 -> "$countTrying попытка"
        0 -> "$countTrying попыток"
        else -> "$countTrying попытки"
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    companion object {
        private val job = SupervisorJob()
        private val toast = ToastShow()
        private val formatter = SimpleDateFormat("HH:mm:ss  dd-MM-yyyy", Locale.ENGLISH)

        @SuppressLint("StaticFieldLeak")
        private val warningFragment = WarningFragment()
        private const val incorrect = "Мастер-пароль введён неверно!"
        private const val Trying = "Trying"
        private const val TimeTrying = "TimeTrying"
    }
}
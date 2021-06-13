@file:Suppress("UNREACHABLE_CODE", "DEPRECATION")

package mpei.vkr.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.camera2.params.LensShadingMap
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mpei.vkr.Constants.*
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.LENGTH
import mpei.vkr.Constants.useBigLetter
import mpei.vkr.Constants.useLittleLetter
import mpei.vkr.Constants.useSpecialSymbols
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.MainActivity
import mpei.vkr.Others.Password
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
            val sp = PreferenceManager.getDefaultSharedPreferences(view.context)
            correct.isCorrectMasterKey(
                _password1.value,
                _password2.value,
                sp.getBoolean(useLittleLetter, true),
                sp.getBoolean(useBigLetter, true),
                sp.getBoolean(useSpecialSymbols, true),
                sp.getString(LENGTH, "8")!!.toInt()
            )
            MasterKey(_password1.value!!).encryptSecretKey()
            val intent = Intent(view.context, MainActivity::class.java)
            intent.putExtra(ARG_MASTER_KEY, _password1.value)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(view.context, intent, null)
        } catch (e: MyException) {
            warningFragment.visible(e.message!!)
            _warning.value = true
        } finally {
            delay(3000L)
            _warning.value = false
            _buttonEnabled.value = true
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    companion object {
        private val job = SupervisorJob()
        @SuppressLint("StaticFieldLeak")
        private val warningFragment = WarningFragment()
        private val correct = Password()
    }
}
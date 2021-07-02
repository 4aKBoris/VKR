package mpei.vkr.ui.settings.items.masterkey.change

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.coroutines.*
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.R
import mpei.vkr.ui.login.WarningFragment
import kotlin.coroutines.CoroutineContext

class OldMasterKeyViewModel : ViewModel(), CoroutineScope {
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
            val masterKey = MasterKey(_password.value!!)
            if (!masterKey.isCorrect()) throw MyException("Мастер-пароль введён неверно!")
            findNavController(view.findFragment()).navigate(R.id.action_oldMasterKeyFragment_to_newMasterKeyFragment,
            Bundle().apply { putString(ARG_MASTER_KEY, _password.value!!) })
        } catch (e: MyException) {
            changeVisible(e.message!!)
        } catch (e: Exception) {
            changeVisible("Мастер-пароль введён неверно!")
        }
    }

    private suspend fun changeVisible(message: String) {
        warningFragment.visible(message)
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


    }
}
@file:Suppress("UNREACHABLE_CODE")

package mpei.vkr.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _password1 = MutableLiveData("")
    private val _password2 = MutableLiveData("")
    private var _visibility = MutableLiveData(false)

    val password1: LiveData<String> = _password1
    val password2: LiveData<String> = _password2
    var visibility: LiveData<Boolean> = _visibility

    fun buttonCheck() {
        _visibility.value = !_visibility.value!!
    }
}
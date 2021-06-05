package mpei.vkr.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel2 : ViewModel() {
    private val _password = MutableLiveData("")
    private var _visibility = MutableLiveData(false)

    val password: LiveData<String> = _password
    var visibility: LiveData<Boolean> = _visibility

    fun buttonCheck() {
        _visibility.value = !_visibility.value!!
    }
}
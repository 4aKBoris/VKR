package mpei.vkr.ui.settings.items.others

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mpei.vkr.Constants.*

class SettingsOthersViewModel(private val sp: SharedPreferences) : ViewModel() {
    private val _salt = MutableLiveData(sp.getBoolean(Salt, false))
    private val _secondPassword = MutableLiveData(sp.getBoolean(SecondPassword, false))
    private val _deleteFile = MutableLiveData(sp.getBoolean(DeleteFile, false))
    private val _passwordFlag = MutableLiveData(sp.getBoolean(PasswordFlag, true))
    private val _cipherPassword = MutableLiveData(sp.getBoolean(CipherPassword, false))

    val salt: MutableLiveData<Boolean> = _salt
    val secondPassword: MutableLiveData<Boolean> = _secondPassword
    val deleteFile: MutableLiveData<Boolean> = _deleteFile
    val passwordFlag: MutableLiveData<Boolean> = _passwordFlag
    val cipherPassword: MutableLiveData<Boolean> = _cipherPassword

    fun saveSettings() {
        val editor = sp.edit()
        editor.putBoolean(Salt, _salt.value!!)
        editor.putBoolean(SecondPassword, _secondPassword.value!!)
        editor.putBoolean(DeleteFile, _deleteFile.value!!)
        editor.putBoolean(PasswordFlag, _passwordFlag.value!!)
        editor.putBoolean(CipherPassword, _cipherPassword.value!!)
        editor.apply()
    }
}
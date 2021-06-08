@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.masterkey

import android.app.Application
import android.content.Intent
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import mpei.vkr.Constants.LENGTH
import mpei.vkr.Constants.useBigLetter
import mpei.vkr.Constants.useLittleLetter
import mpei.vkr.Constants.useSpecialSymbols
import mpei.vkr.ui.settings.items.masterkey.change.ChangeMasterKeyActivity

class SettingsMasterKeyViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    private val sp = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    private val _littleLetter = MutableLiveData(sp.getBoolean(useLittleLetter, true))
    private val _bigLetter = MutableLiveData(sp.getBoolean(useBigLetter, true))
    private val _specialSymbols = MutableLiveData(sp.getBoolean(useSpecialSymbols, true))
    private val _length = MutableLiveData(sp.getString(LENGTH, "8")!!)

    val littleLetter: MutableLiveData<Boolean> = _littleLetter
    val bigLetter: MutableLiveData<Boolean> = _bigLetter
    val specialSymbols: MutableLiveData<Boolean> = _specialSymbols
    val length: MutableLiveData<String> = _length

    fun buttonChange(view: View) {
        val intent = Intent(view.context, ChangeMasterKeyActivity::class.java)
        startActivity(view.context, intent, null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun saveSettings() {
        val editor = sp.edit()
        editor.putBoolean(useLittleLetter, _littleLetter.value!!)
        editor.putBoolean(useBigLetter, _bigLetter.value!!)
        editor.putBoolean(useSpecialSymbols, _specialSymbols.value!!)
        editor.putString(LENGTH, _length.value!!)
        editor.apply()
    }
}
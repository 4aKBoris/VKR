@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.message.digest

import android.app.Application
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import mpei.vkr.Constants.*

class SettingsMessageDigestViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver {
    private val sp = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    private val _hashAlgorithm = MutableLiveData(sp.getString(HashAlgorithm, SHA256)!!)
    private val _hashCount = MutableLiveData(sp.getInt(HashCount, 1))

    val hashAlgorithm: MutableLiveData<String> = _hashAlgorithm
    val hashCount: MutableLiveData<Int> = _hashCount

    fun chooseAlgorithm(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle("Выберите алгоритм хэш-функции")
        builder.setItems(hashAlg.toTypedArray()) { _, which ->
            _hashAlgorithm.value = hashAlg[which]
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun saveSettings() {
        val editor = sp.edit()
        editor.putString(HashAlgorithm, _hashAlgorithm.value!!)
        editor.putInt(HashCount, _hashCount.value!!)
        editor.apply()
    }

}
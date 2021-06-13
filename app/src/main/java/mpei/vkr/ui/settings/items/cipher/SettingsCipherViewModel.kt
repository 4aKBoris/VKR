@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.cipher

import android.app.Application
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import mpei.vkr.Constants.*
import mpei.vkr.Crypto.Algorithms


class SettingsCipherViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver {
    private val sp = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    private val _cipherAlgorithm = MutableLiveData(sp.getString(CipherAlgorithm, AES)!!)
    private val _cipherCount = MutableLiveData(sp.getInt(CipherCount, 1))
    private val _keySize = MutableLiveData(sp.getInt(KeySize, 32))

    val cipherAlgorithm: MutableLiveData<String> = _cipherAlgorithm
    val cipherCount: MutableLiveData<Int> = _cipherCount
    val keySize: MutableLiveData<Int> = _keySize
    val keySizeMin: LiveData<Int> = MutableLiveData(0)
    val keySizeMax: LiveData<Int> =
        Transformations.map(_cipherAlgorithm) { (alg.getKeySizeMax(it) - alg.getKeySizeMin(it)) / alg.getKeySizeStep(it) }
    val step: LiveData<Int> =
        Transformations.map(_cipherAlgorithm) { (_keySize.value!! - alg.getKeySizeMin(it)) / alg.getKeySizeStep(it) }
    val keySizeMinText: LiveData<String> =
        Transformations.map(_cipherAlgorithm) { alg.getKeySizeMin(it).toString() }
    val keySizeMaxText: LiveData<String> =
        Transformations.map(_cipherAlgorithm) { alg.getKeySizeMax(it).toString() }

    fun chooseAlgorithm(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle("Выберите алгоритм шифрования")
        builder.setItems(alg.getCipherAlgorithms().toTypedArray()) { _, which ->
            val cipherAlgorithm = alg.getCipherAlgorithm(which)
            _keySize.value = alg.getKeySizeMax(cipherAlgorithm)
            _cipherAlgorithm.value = cipherAlgorithm
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun saveSettings() {
        val editor = sp.edit()
        editor.putString(CipherAlgorithm, _cipherAlgorithm.value!!)
        editor.putInt(CipherCount, _cipherCount.value!!)
        editor.putInt(KeySize, _keySize.value!!)
        editor.apply()
    }

    companion object {
        private val alg = Algorithms()
    }
}
@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.signature

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.findFragment
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.findNavController
import mpei.vkr.Constants.*
import mpei.vkr.Constants.HashAlgorithm
import mpei.vkr.Constants.HashCount
import mpei.vkr.Constants.SHA256
import mpei.vkr.Constants.SignatureAlgorithm
import mpei.vkr.R

class SettingsSignatureViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver {
    private val sp = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    private val _signatureAlgorithm =
        MutableLiveData(sp.getString(SignatureAlgorithm, SHA256withRSA)!!)

    val signatureAlgorithm: MutableLiveData<String> = _signatureAlgorithm

    fun chooseAlgorithm(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle("Выберите алгоритм цифровой подписи")
        builder.setItems(sign.toTypedArray()) { _, which ->
            _signatureAlgorithm.value = sign[which]
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun buttonCertificate(view: View) {
        findNavController(view.findFragment()).navigate(R.id.action_nav_settings_to_nav_certificate)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun saveSettings() {
        val editor = sp.edit()
        editor.putString(SignatureAlgorithm, _signatureAlgorithm.value!!)
        editor.apply()
    }

}
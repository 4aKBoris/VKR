@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.lockdown

import android.annotation.SuppressLint
import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.*
import mpei.vkr.Constants.CountHours
import mpei.vkr.Constants.CountTrying

class LockdownViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val sp = PreferenceManager.getDefaultSharedPreferences(context)
    private val _hours = MutableLiveData(sp.getInt(CountHours, 24))
    private val _count = MutableLiveData(sp.getInt(CountTrying, 3))

    val hours: MutableLiveData<Int> = _hours
    val count: MutableLiveData<Int> = _count
    val steps: LiveData<Int> = Transformations.map(_count) { it - 1 }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun saveSettings() {
        sp.edit().apply {
           putInt(CountHours, _hours.value!!)
           putInt(CountTrying, _count.value!!)
           apply()
        }
    }
}
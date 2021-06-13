@file:Suppress("DEPRECATION")

package mpei.vkr.ui.choice.certificate

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.coroutines.*
import mpei.vkr.Constants.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.KeyStoreClass
import mpei.vkr.Others.Notification
import mpei.vkr.Others.ToastShow
import mpei.vkr.R
import java.io.File
import kotlin.coroutines.CoroutineContext

class CertificateViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val _pathCertificate = MutableLiveData("")
    private val sp = PreferenceManager.getDefaultSharedPreferences(context)

    val pathCertificate: MutableLiveData<String> = _pathCertificate
    val certificateName: LiveData<String> = Transformations.map(_pathCertificate) { File(it).name }
    private val password = sp.getString(MasterKey, "")!!
    private val keyStore = KeyStoreClass(password)

    @RequiresApi(Build.VERSION_CODES.O)
    fun import() = launch(Dispatchers.IO) {
        try {
            if (_pathCertificate.value == "") throw MyException("Выбирите файл!")
            keyStore.importCertificate(_pathCertificate.value!!)
            toast.suspendShow(context, "Сертификат ${File(_pathCertificate.value!!).name} импортирован!")
        } catch (e: MyException) {
            toast.suspendShow(context, e.message!!)
        } catch (e: Exception) {
            toast.suspendShow(context, "Данный файл не является сертификатом!")
        }
    }

    fun export(context: Context) = launch(Dispatchers.IO) {
        try {
            var alg: String?
            val algorithms = keyStore.algorithms()
            if (algorithms.isNullOrEmpty()) throw MyException("Создайте сертификаты!")
            withContext(Dispatchers.Main) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Выберите сертификат")
                builder.setItems(algorithms) { dialog, which ->
                    alg = algorithms[which]
                    dialog.dismiss()
                    if (alg == null) toast.show(
                        context,
                        "Сертификат с алгоритмом $alg экспортирован!"
                    )
                    else {
                        keyStore.saveCertificate("my_cert_$alg.cer", keyStore.getCertificate(alg!!))
                        toast.show(context, "Сертификат с алгоритмом $alg экспортирован!")
                        Notification(context)
                    }
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        } catch (e: MyException) {
            toast.suspendShow(context, e.message!!)
        }
    }

    fun createCertificate(fragment: CertificateFragment) =
        launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                if (keyStore.isHaveCertificates()) findNavController(fragment).navigate(R.id.action_nav_certificate_to_createCertificateFragment)
                else dialog(fragment)
            }
        }

    private fun dialog(fragment: CertificateFragment) {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> findNavController(fragment).navigate(R.id.action_nav_certificate_to_createCertificateFragment)
                    DialogInterface.BUTTON_NEGATIVE -> dialog.cancel()
                }
            }
        val builder = android.app.AlertDialog.Builder(fragment.context)
        builder.setMessage("Сертификаты уже есть в хранилище, создать новые?")
            .setPositiveButton("Да", dialogClickListener)
            .setNegativeButton("Нет", dialogClickListener).show()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    companion object {
        private val job = SupervisorJob()
        private val toast = ToastShow()
    }
}
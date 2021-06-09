package mpei.vkr.ui.choice.certificate

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.coroutines.*
import mpei.vkr.Constants.*
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileReadWrite
import mpei.vkr.Others.KeySt
import mpei.vkr.Others.Notification
import mpei.vkr.Others.ToastShow
import mpei.vkr.R
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import kotlin.coroutines.CoroutineContext

class CertificateViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val _pathCertificate = MutableLiveData("")

    val pathCertificate: MutableLiveData<String> = _pathCertificate
    val certificateName: LiveData<String> = Transformations.map(_pathCertificate) { File(it).name }

    fun import() = launch(Dispatchers.IO) {
        try {
            val certificate = FileReadWrite(_pathCertificate.value!!).readFile()
            val file = FileReadWrite(pathToCertificate + File(_pathCertificate.value!!).name)
            file.writeFile(certificate)
        } catch (e: MyException) {
            toast.suspendShow(context, e.message!!)
        } catch (e: Exception) {
            toast.suspendShow(context, "Ошибка с чтением или записью сертфииката!")
        }
    }

    fun export(context: Context) = launch(Dispatchers.IO) {
        try {
            var alg: String?
            val keyStore = KeySt(password)
            val algorithms = keyStore.algorithms()
            if (algorithms.isNullOrEmpty()) throw MyException("Создайте сертификаты!")
            withContext(Dispatchers.Main) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Выберите сертификат")
                builder.setItems(algorithms) { dialog, which ->
                    alg = algorithms[which]
                    dialog.dismiss()
                    if (alg == null) toast.show(context, "Сертификат с алгоритмом $alg экспортирован!")
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
            val keyStoreData = FileInputStream(PATH_KEY_STORE)
            val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
            keyStore.load(keyStoreData, password.toCharArray())
            keyStore.aliases().toList().forEach { Log.d(LOG_TAG, it) }
            withContext(Dispatchers.Main) {
                if (keyStore.aliases().toList().find { it.contains(Certificate) }
                        .isNullOrEmpty()) findNavController(fragment).navigate(R.id.action_nav_certificate_to_createCertificateFragment)
                else dialog(fragment)
            }
        }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    companion object {
        private val job = SupervisorJob()
        private const val password = "12345678"
        private val toast = ToastShow()

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
    }
}
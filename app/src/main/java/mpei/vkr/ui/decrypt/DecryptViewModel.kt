@file:Suppress("DEPRECATION")

package mpei.vkr.ui.decrypt

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.findFragment
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.coroutines.*
import mpei.vkr.Constants.*
import mpei.vkr.Constants.SHA256withRSA
import mpei.vkr.Constants.UseMasterKey
import mpei.vkr.Constants.path
import mpei.vkr.Crypto.Decrypt
import mpei.vkr.Crypto.MetaData
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileClass
import mpei.vkr.Others.KeyStoreClass
import mpei.vkr.Others.ToastShow
import mpei.vkr.R
import mpei.vkr.ui.encrypt.EncryptViewModel
import kotlin.Exception
import kotlin.coroutines.CoroutineContext

class DecryptViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val sp = PreferenceManager.getDefaultSharedPreferences(context)
    private val _password = MutableLiveData("")
    private val _fileName = MutableLiveData("")
    private val _decrypt = MutableLiveData(false)
    private val _useMasterKey = MutableLiveData(sp.getBoolean(UseMasterKey, true))
    private val _signature = MutableLiveData(sp.getString(SignatureAlgorithm, SHA256withRSA)!!)
    private val _certificate = MutableLiveData("Выберите сертификат")

    val password: MutableLiveData<String> = _password
    val fileName: MutableLiveData<String> = _fileName
    val decrypt: LiveData<Boolean> = _decrypt
    val fileSize: LiveData<String> = Transformations.map(_fileName) {
        if (it != "") GlobalScope.launch(Dispatchers.Main) { meta(it) }
        file.fileSize(pathToCipherFiles + it)
    }
    val useMasterKey: LiveData<Boolean> = _useMasterKey
    val signature: LiveData<String> = _signature
    val certificate: LiveData<String> = _certificate
    val certificateVisibility: LiveData<Boolean> = Transformations.map(_signature) { it == NotUse }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    fun decryptFile(view: View) = launch(Dispatchers.Default) {
        decryptProgressBar(true)
        try {
            val dec = Decrypt(
                metaData,
                _certificate.value,
                pass,
                _password.value,
                context,
                _fileName.value
            )
            dec.decrypt()
            toast.suspendShow(view.context, "Файл расшифрован!")
        } catch (e: MyException) {
            toast.suspendShow(view.context, e.message!!)
        } catch (e: Exception) {
            toast.suspendShow(context, e.message!!)
            Log.d(LOG_TAG, e.message!!)
        } finally {
            decryptProgressBar(false)
        }
    }

    private suspend fun decryptProgressBar(flag: Boolean) = withContext(Dispatchers.Main) {
        _decrypt.value = flag
    }


    fun choiceFile(view: View) {
        findNavController(view.findFragment())
            .navigate(R.id.action_nav_decrypt_to_fileFragment)
    }

    fun chooseCertificate(view: View) {
        try {
            val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
            builder.setTitle("Выберите сертификат для шифрования пароля")
            val keyStore = KeyStoreClass(pass)
            val names = keyStore.certificateNames()
            if (names.isEmpty()) throw MyException("Импортируйте сертификат или создайте свои!")
            builder.setItems(names) { _, which -> _certificate.value = names[which] }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } catch (e: MyException) {
            toast.show(view.context, e.message!!)
        }
    }

    private suspend fun meta(fileName: String) {
        try {
            val m = MetaData(file.readFile(pathToCipherFiles + fileName))
            metaData = m.getMetaData()
            _signature.value = metaData.signatureAlgorithm
            Log.d(LOG_TAG, _signature.value!!)
            _useMasterKey.value = metaData.masterKey
        } catch (e: MyException) {
            toast.suspendShow(context, e.message!!)
        } catch (e: Exception) {
            toast.suspendShow(context, e.message!!)
        }
    }

    companion object {
        private val job = SupervisorJob()
        private val file = FileClass()
        private val toast = ToastShow()
        private const val pass = "12345678"
        private val VKR = listOf<Byte>(86, 75, 82)
        private lateinit var metaData: MetaData
    }
}
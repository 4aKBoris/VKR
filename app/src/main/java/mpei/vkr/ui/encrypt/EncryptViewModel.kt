@file:Suppress(
    "DEPRECATION", "BlockingMethodInNonBlockingContext",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)

package mpei.vkr.ui.encrypt

import android.annotation.SuppressLint
import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.*
import mpei.vkr.Constants.*
import mpei.vkr.Crypto.Encrypt
import mpei.vkr.Crypto.MetaData
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileClass
import mpei.vkr.Others.ToastShow
import kotlin.coroutines.CoroutineContext

class EncryptViewModel(application: Application) : AndroidViewModel(application), CoroutineScope,
    LifecycleObserver {

    private val sp = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    private val metaData = MetaData(
        sp.getString(CipherAlgorithm, AES)!!,
        sp.getInt(CipherCount, 1),
        sp.getInt(KeySize, 32),
        sp.getBoolean(UseMasterKey, true),
        sp.getString(SignatureAlgorithm, SHA256withRSA)!!
    )
    private val _password1 = MutableLiveData("")
    private val _password2 = MutableLiveData("")
    private val _fileName = MutableLiveData("")
    private val _encrypt = MutableLiveData(false)
    private val _secondPassword = MutableLiveData(sp.getBoolean(SecondPassword, true))
    private val _cipherPassword = MutableLiveData(sp.getBoolean(CipherPassword, true))
    private val _masterKey = MutableLiveData(metaData.masterKey)
    private val _password = MutableLiveData(sp.getString(MasterKey, "")!!)

    val password1: MutableLiveData<String> = _password1
    val password2: MutableLiveData<String> = _password2
    val password: MutableLiveData<String> = _password
    val fileName: MutableLiveData<String> = _fileName
    val encrypt: LiveData<Boolean> = _encrypt
    val cipherPassword: LiveData<Boolean> = _cipherPassword
    val fileSize: LiveData<String> = Transformations.map(_fileName) { file.fileSize(it) }
    val masterKey: LiveData<Boolean> = _masterKey
    val visibilityPassword1: LiveData<Boolean> = MutableLiveData(!_masterKey.value!!)
    val visibilityPassword2: LiveData<Boolean> =
        MutableLiveData(!_masterKey.value!! && _secondPassword.value!!)

    private val passwordFlag = sp.getBoolean(PasswordFlag, true)
    private val saltFlag = sp.getBoolean(Salt, false)
    private val hashAlgorithm = sp.getString(HashAlgorithm, SHA256)!!
    private val hashCount = sp.getInt(HashCount, 1)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    @SuppressLint("CommitPrefEdits")
    fun encryptFile(view: View) = launch(Dispatchers.Default) {
        try {
            val enc = Encrypt(
                metaData,
                _fileName.value,
                _masterKey.value!!,
                cipherPassword.value!!,
                passwordFlag,
                _password1.value,
                _password2.value,
                _secondPassword.value!!,
                saltFlag,
                hashAlgorithm,
                hashCount,
                view.context,
                _password.value!!
            )
            encryptProgressBar(true)
            enc.encrypt()
            toast.suspendShow(view.context, "Файл зашифрован!")
        } catch (e: MyException) {
            toast.suspendShow(view.context, e.message!!)
        } catch (e: Exception) {
            println(e.message!!)
            Log.d(LOG_TAG, e.message!!)
            Log.d(LOG_TAG, e.localizedMessage)
        } finally {
            encryptProgressBar(false)
        }
    }

    private suspend fun encryptProgressBar(flag: Boolean) = withContext(Dispatchers.Main) {
        _encrypt.value = flag
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun clear() {
        onCleared()
    }

    companion object {
        private val job = SupervisorJob()
        private val file = FileClass()
        private val toast = ToastShow()
    }
}
@file:Suppress("DEPRECATION", "BlockingMethodInNonBlockingContext")

package mpei.vkr.ui.encrypt

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileReadWrite
import mpei.vkr.Settings.Settings
import java.io.File
import kotlin.coroutines.CoroutineContext


class EncryptViewModel : ViewModel(), CoroutineScope {

    private var settings = Settings().loadSettings()
    private val _password1 = MutableLiveData("")
    private val _password2 = MutableLiveData("")
    private val _fileName = MutableLiveData("")
    private val _encrypt = MutableLiveData(false)
    private val _secondPassword = MutableLiveData(settings.secondPassword)
    private val _signature = MutableLiveData(settings.signature)

    val password1: MutableLiveData<String> = _password1
    val password2: MutableLiveData<String> = _password2
    val fileName: MutableLiveData<String> = _fileName
    val encrypt: LiveData<Boolean> = _encrypt
    val secondPassword: LiveData<Boolean> = _secondPassword
    val signature: LiveData<String> = _signature
    val fileSize: LiveData<String> = Transformations.map(_fileName) { FileReadWrite(it).fileSize() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    fun encryptFile(view: View) = launch(Dispatchers.Main) {
        _encrypt.value = true
        val file = FileReadWrite(_fileName.value!!)
        Log.d("Шифрование", _fileName.value!!)
        Log.d("Шифрование", file.readFile().contentToString())
        try {
            //val file = FileReadWrite(fileName.value!!)
            val arr: ByteArray
            withContext(Dispatchers.Default) {
                settings = Settings().loadSettings()
                //arr = file.readFile()
                delay(1000L)
                //val mas = arr
            }
        } catch (e: MyException) {
            toast(view.context, e.message!!)
        }
        _encrypt.value = false
        toast(view.context, "Файл зашифрован!")
    }

    private fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val job = SupervisorJob()
    }
}
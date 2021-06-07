package mpei.vkr.ui.decrypt

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import mpei.vkr.Constants.path
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileReadWrite
import mpei.vkr.R
import kotlin.coroutines.CoroutineContext

class DecryptViewModel : ViewModel(), CoroutineScope {

    private val _password = MutableLiveData("")
    private val _fileName = MutableLiveData("")
    private val _decrypt = MutableLiveData(false)
    private val _signature = MutableLiveData("Не использовать")

    val password: MutableLiveData<String> = _password
    val fileName: MutableLiveData<String> = _fileName
    val decrypt: LiveData<Boolean> = _decrypt
    val signature: LiveData<String> = _signature
    val fileSize: LiveData<String> = Transformations.map(_fileName) { FileReadWrite(path + it).fileSize() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    fun decryptFile(view: View) = launch(Dispatchers.Main) {
        _decrypt.value = true
        val file = FileReadWrite(_fileName.value!!)
        Log.d("Шифрование", _fileName.value!!)
        Log.d("Шифрование", file.readFile().contentToString())
        try {
            //val file = FileReadWrite(fileName.value!!)
            val arr: ByteArray
            withContext(Dispatchers.Default) {

                //arr = file.readFile()
                delay(1000L)
                //val mas = arr
            }
        } catch (e: MyException) {
            toast(view.context, e.message!!)
        }
        _decrypt.value = false
        toast(view.context, "Файл расшифрован!")
    }

    fun choiceFile(view: View) {
        findNavController(view.findFragment())
            .navigate(R.id.action_nav_decrypt_to_fileFragment)
    }

    private fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val job = SupervisorJob()
    }
}
@file:Suppress("DEPRECATION", "UNREACHABLE_CODE")

package mpei.vkr.ui.choice.certificate

import android.provider.Settings.Secure.ANDROID_ID
import android.util.Log
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.coroutines.*
import mpei.vkr.Constants.*
import mpei.vkr.Others.ToastShow
import mpei.vkr.R
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.x509.X509V3CertificateGenerator
import java.io.FileInputStream
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Security
import java.util.*
import javax.security.auth.x500.X500Principal
import kotlin.coroutines.CoroutineContext


@Suppress("SameParameterValue")
class CreateCertificateViewModel : ViewModel(), CoroutineScope, LifecycleObserver {

    private val _name = MutableLiveData("")
    private val _organizationalUnit = MutableLiveData("")
    private val _organizationName = MutableLiveData("")
    private val _locality = MutableLiveData("")
    private val _state = MutableLiveData("")
    private val _progress = MutableLiveData(0)

    val name: MutableLiveData<String> = _name
    val organizationalUnit: MutableLiveData<String> = _organizationalUnit
    val organizationName: MutableLiveData<String> = _organizationName
    val locality: MutableLiveData<String> = _locality
    val state: MutableLiveData<String> = _state
    val progress: LiveData<Int> = _progress

    fun onButtonCreate(view: View) = launch(Dispatchers.IO) {
        try {
            createCertificates()
            toast.suspendShow(view.context, "Сертификаты успешно созданы!")
            findNavController(view.findFragment()).navigate(R.id.action_createCertificateFragment_to_nav_home)
        } catch (e: Exception) {
            toast.suspendShow(view.context, e.message!!)
        }
    }

    private suspend fun createCertificates() {
        val keyStoreData = FileInputStream(PATH_KEY_STORE)
        val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
        keyStore.load(keyStoreData, password.toCharArray())
        sign.filter { it != "Не использовать" }.forEach {
            Security.addProvider(BouncyCastleProvider())
            val keyPairGenerator = KeyPairGenerator.getInstance(s(it))
            val keyPair = keyPairGenerator.generateKeyPair()
            val gen = X509V3CertificateGenerator()
            val serverCommonName = X500Principal("CN=${_name.value}")
            val server =
                X500Principal("CN=${_name.value}, OU=${_organizationalUnit.value}, O=${_organizationName.value}, L=${_locality.value},  ST=${_state.value}, C=RU")
            gen.setSerialNumber(BigInteger(ANDROID_ID.toByteArray()))
            val after = Date(2030, 1, 1, 0, 0, 0)
            val before = Calendar.getInstance().time
            gen.setIssuerDN(serverCommonName)
            gen.setNotBefore(before)
            gen.setNotAfter(after)
            gen.setSubjectDN(serverCommonName)
            gen.setSubjectDN(server)
            gen.setPublicKey(keyPair.public)
            gen.setSignatureAlgorithm(it)
            val myCert = gen.generate(keyPair.private)
            keyStore.setCertificateEntry(Certificate + it, myCert)
            keyStore.setKeyEntry(it, keyPair.private, null, arrayOf(myCert))
            setProgress()
        }
        val keyStoreOutputStream = FileOutputStream(PATH_KEY_STORE)
        keyStore.store(keyStoreOutputStream, password.toCharArray())
    }

    private suspend fun setProgress() {
        withContext(Dispatchers.Main) { _progress.value = _progress.value!! + 1 }
        Log.d(LOG_TAG, _progress.value!!.toString())
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun reset() {
        _progress.value = 0
    }

    companion object {
        private const val password = "12345678"
        private val job = SupervisorJob()
        private var toast = ToastShow()

        @JvmStatic
        private fun s(sign_alg: String): String {
            return when {
                sign_alg.indexOf("EC") != -1 -> "EC"
                sign_alg.indexOf("with") == -1 -> sign_alg
                else -> sign_alg.substring(sign_alg.indexOf("with") + 4)
            }
        }
    }

}
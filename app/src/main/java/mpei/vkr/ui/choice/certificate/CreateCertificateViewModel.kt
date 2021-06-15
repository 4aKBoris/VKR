@file:Suppress("DEPRECATION", "UNREACHABLE_CODE")

package mpei.vkr.ui.choice.certificate

import android.preference.PreferenceManager
import android.provider.Settings.Secure.ANDROID_ID
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.*
import mpei.vkr.Constants.Certificate
import mpei.vkr.Constants.PrivateKey
import mpei.vkr.Constants.SHA256withRSA
import mpei.vkr.Constants.SecretKey
import mpei.vkr.Crypto.Algorithms
import mpei.vkr.Others.KeyStoreClass
import mpei.vkr.Others.ToastShow
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.x509.X509V3CertificateGenerator
import java.math.BigInteger
import java.security.KeyPairGenerator
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
            val sp = PreferenceManager.getDefaultSharedPreferences(view.context)
            createCertificates(sp.getString(mpei.vkr.Constants.MasterKey, "")!!)
            toast.suspendShow(view.context, "Сертификаты успешно созданы!")
        } catch (e: Exception) {
            toast.suspendShow(view.context, e.message!!)
        }
    }

    private suspend fun createCertificates(masterKey: String) {
        val keyStore = KeyStoreClass(masterKey)
        val a = alg.getSignatureAlgorithms().filter { it != "Не использовать" }.toMutableList()
        a.add(SecretKey)
        a.forEach {
            if (it != SecretKey) generateCertificate(it, it, keyStore)
            else generateCertificate(SHA256withRSA, it, keyStore)
        }
        keyStore.saveKeyStore()
    }

    private suspend fun setProgress() {
        withContext(Dispatchers.Main) { _progress.value = _progress.value!! + 1 }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun reset() {
        _progress.value = 0
    }

    private suspend fun generateCertificate(alg: String, alias: String, keyStore: KeyStoreClass) {
        Security.addProvider(BouncyCastleProvider())
        val keyPairGenerator = KeyPairGenerator.getInstance(s(alg))
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
        gen.setSignatureAlgorithm(alg)
        val myCert = gen.generate(keyPair.private)
        keyStore.setCertificate(myCert, Certificate + alias)
        keyStore.setPrivateKey(keyPair.private, PrivateKey + alias, myCert)
        setProgress()
    }

    companion object {
        private val job = SupervisorJob()
        private var toast = ToastShow()
        private val alg = Algorithms()

        private fun s(alg: String) = when {
            alg.contains("ECDSA") -> "EC"
            alg.contains("RSA") -> "RSA"
            alg.contains("DSA") -> "DSA"
            else -> "RSA"
        }
    }
}
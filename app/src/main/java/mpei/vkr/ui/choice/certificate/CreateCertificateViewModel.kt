@file:Suppress("DEPRECATION", "UNREACHABLE_CODE")

package mpei.vkr.ui.choice.certificate

import android.content.Context
import android.provider.Settings.Secure.ANDROID_ID
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mpei.vkr.Constants.KEY_STORE_ALGORITHM
import mpei.vkr.Constants.PATH_KEY_STORE
import mpei.vkr.Constants.sign
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.x509.X509V3CertificateGenerator
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Security
import java.util.*
import javax.security.auth.x500.X500Principal
import kotlin.coroutines.CoroutineContext

class CreateCertificateViewModel : ViewModel(), CoroutineScope {

    private val _name = MutableLiveData("")
    private val _organizationalUnit = MutableLiveData("")
    private val _organizationName = MutableLiveData("")
    private val _locality = MutableLiveData("")
    private val _state = MutableLiveData("")

    val name: MutableLiveData<String> = _name
    val organizationalUnit: MutableLiveData<String> = _organizationalUnit
    val organizationName: MutableLiveData<String> = _organizationName
    val locality: MutableLiveData<String> = _locality
    val state: MutableLiveData<String> = _state

    fun onButtonCreate(view: View) = launch(Dispatchers.IO) {
        createCertificates()
        toast(view.context, "Сертификаты успешно созданы!")
    }


    private fun createCertificates() {
        val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
        keyStore.load(null, password.toCharArray())
        sign.filter { it != "Не использовать" }.forEach {
            Security.addProvider(BouncyCastleProvider())
            val keyPairGenerator = KeyPairGenerator.getInstance(Alg(it))
            val keyPair = keyPairGenerator.generateKeyPair()
            val gen = X509V3CertificateGenerator()
            val serverCommonName = X500Principal("CN=${name}")
            val serverOrganizationalUnit = X500Principal("OU=${organizationalUnit}")
            val serverOrganizationName = X500Principal("O=${organizationName}")
            val serverLocality = X500Principal("L=${locality}")
            val serverState = X500Principal("ST=${state}")
            val serverCountry = X500Principal("C=RU")
            gen.setSerialNumber(BigInteger(ANDROID_ID.toByteArray()))
            val after = Date(2030, 1, 1, 0, 0, 0)
            val before = Date()
            gen.setIssuerDN(serverCommonName)
            gen.setNotBefore(after)
            gen.setNotAfter(before)
            gen.setSubjectDN(serverCommonName)
            gen.setSubjectDN(serverOrganizationalUnit)
            gen.setSubjectDN(serverOrganizationName)
            gen.setSubjectDN(serverLocality)
            gen.setSubjectDN(serverState)
            gen.setSubjectDN(serverCountry)
            gen.setPublicKey(keyPair.public)
            gen.setSignatureAlgorithm(it)
            val myCert = gen.generate(keyPair.private)
            keyStore.setCertificateEntry(it, myCert)
            keyStore.setKeyEntry(it, keyPair.private, null, arrayOf(myCert))
        }
        val keyStoreOutputStream = FileOutputStream(PATH_KEY_STORE)
        keyStore.store(keyStoreOutputStream, password.toCharArray())
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    private suspend fun toast(context: Context, message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val password = "12345678"
        private val job = SupervisorJob()

        private fun Alg(sign_alg: String): String {
            return when {
                sign_alg.indexOf("EC") != -1 -> "EC"
                sign_alg.indexOf("with") == -1 -> sign_alg
                else -> sign_alg.substring(sign_alg.indexOf("with") + 4)
            }
        }
    }

}
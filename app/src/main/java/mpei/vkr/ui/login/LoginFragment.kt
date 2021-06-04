package mpei.vkr.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.pathMasterKey
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.MainActivity
import mpei.vkr.R
import mpei.vkr.Regex.RegexExpr
import java.io.File

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        if (File(pathMasterKey).exists()) NavHostFragment.findNavController(this)
            .navigate(R.id.action_loginFragment_to_loginFragment2)
        buttonConfirm = view.findViewById(R.id.buttonConf)
        password1EditText = view.findViewById(R.id.editTextPassword1)
        password2EditText = view.findViewById(R.id.editTextPassword2)
        checkBoxVisible = view.findViewById(R.id.checkBoxVisible)
        fragmentContainerView = view.findViewById(R.id.fragmentContainerView2)

        buttonConfirm.setOnClickListener {
            fragmentContainerView.visibility = View.INVISIBLE
            val password1 = password1EditText.text.toString()
            val password2 = password2EditText.text.toString()
            try {
                if (password1.isEmpty() || password2.isEmpty()) throw MyException("Введите мастер ключ!")
                if (!regex.regLittle.containsMatchIn(password1) || !regex.regBig.containsMatchIn(
                        password1
                    ) || !regex.regSpecial.containsMatchIn(password1)
                ) throw MyException("Мастер ключ не соответствует требованиям!")
                val mk = MasterKey(password1)
                mk.CipherSecretKey()
                val intent = Intent(view.context, MainActivity::class.java)
                intent.putExtra(ARG_MASTER_KEY, password1)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } catch (e: MyException) {
                warningFragment.Visible(e.message!!)
                fragmentContainerView.visibility = View.VISIBLE
                @Suppress("DEPRECATION")
                Handler().postDelayed({
                    fragmentContainerView.visibility = View.INVISIBLE
                }, 5000)
            }
        }

        checkBoxVisible.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                password1EditText.transformationMethod = null
                password2EditText.transformationMethod = null
            } else {
                password1EditText.transformationMethod = PasswordTransformationMethod()
                password2EditText.transformationMethod = PasswordTransformationMethod()
            }
            if (password1EditText.isFocused) password1EditText.setSelection(
                password1EditText.text.toString().length
            )
            if (password2EditText.isFocused) password2EditText.setSelection(
                password2EditText.text.toString().length
            )
        }

        return view
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var buttonConfirm: Button

        @SuppressLint("StaticFieldLeak")
        private lateinit var password1EditText: EditText

        @SuppressLint("StaticFieldLeak")
        private lateinit var password2EditText: EditText
        private val regex = RegexExpr()

        @SuppressLint("StaticFieldLeak")
        private lateinit var checkBoxVisible: ToggleButton

        @SuppressLint("StaticFieldLeak")
        private val warningFragment = WarningFragment()

        private lateinit var fragmentContainerView: FragmentContainerView
    }
}
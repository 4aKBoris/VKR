@file:Suppress("UNREACHABLE_CODE", "DEPRECATION")

package mpei.vkr.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProviders
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.MainActivity
import mpei.vkr.R
import mpei.vkr.databinding.FragmentLogin2Binding
import mpei.vkr.databinding.FragmentLoginBinding
import kotlin.jvm.Throws


class LoginFragment2 : DialogFragment() {

    private var _binding: FragmentLogin2Binding? = null
    private val loginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel2::class.java)
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        binding.login = loginViewModel
        binding.lifecycleOwner = this

        binding.buttonConf.setOnClickListener {
            binding.fragmentContainerView.visibility = View.INVISIBLE
            try {
                val password = binding.editTextPassword.text.toString()
                isCorrect(password)
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.putExtra(ARG_MASTER_KEY, password)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } catch (e: MyException) {
                warningFragment.Visible(e.message!!)
                changeVisible()
            } catch (e: Exception) {
                warningFragment.Visible("Мастер ключ введён неверно!")
                changeVisible()
            }
        }
        return binding.root
    }

    private fun changeVisible() {
        binding.fragmentContainerView.visibility = View.VISIBLE
        @Suppress("DEPRECATION")
        Handler().postDelayed({
            binding.fragmentContainerView.visibility = View.INVISIBLE
        }, 5000)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private val warningFragment = WarningFragment()

        @Throws(MyException::class)
        private fun isCorrect(pass: String) {
            val mk = MasterKey(pass)
            if (pass.isEmpty()) throw MyException("Введите мастер ключ!")
            if (!mk.IsCorrect()) throw MyException("Мастер ключ введён неверно!")
        }
    }
}
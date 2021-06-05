@file:Suppress("DEPRECATION")

package mpei.vkr.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.pathMasterKey
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.MainActivity
import mpei.vkr.R
import mpei.vkr.Regex.RegexExpr
import mpei.vkr.databinding.FragmentLoginBinding
import java.io.File
import kotlin.jvm.Throws

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val loginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        if (File(pathMasterKey).exists()) findNavController()
            .navigate(R.id.action_loginFragment_to_loginFragment2)

        binding.login = loginViewModel
        binding.lifecycleOwner = this
        binding.buttonConf.setOnClickListener {
            binding.fragmentContainerView2.visibility = View.INVISIBLE
            try {
                isCorrect(
                    binding.editTextPassword1.text.toString(),
                    binding.editTextPassword2.text.toString()
                )
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.putExtra(ARG_MASTER_KEY, "Dwa")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } catch (e: MyException) {
                warningFragment.Visible(e.message!!)
                binding.fragmentContainerView2.visibility = View.VISIBLE
                @Suppress("DEPRECATION")
                Handler().postDelayed({
                    binding.fragmentContainerView2.visibility = View.INVISIBLE
                }, 5000)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private val warningFragment = WarningFragment()
        private val regex = RegexExpr()

        @Throws(MyException::class)
        private fun isCorrect(pass1: String, pass2: String) {
            if (pass1.isEmpty() || pass2.isEmpty()) throw MyException("Введите мастер ключ!")
            if (pass1 != pass2) throw MyException("Введённые мастер ключи не совпадают!")
            if (!regex.regLittle.containsMatchIn(pass1) || !regex.regBig.containsMatchIn(
                    pass1
                ) || !regex.regSpecial.containsMatchIn(pass1)
            ) throw MyException("Мастер ключ не соответствует требованиям!")
            val mk = MasterKey(pass1)
            mk.CipherSecretKey()
        }
    }
}
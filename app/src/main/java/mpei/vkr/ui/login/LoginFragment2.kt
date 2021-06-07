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
        return binding.root
    }
}
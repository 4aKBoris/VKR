@file:Suppress("UNREACHABLE_CODE", "DEPRECATION")

package mpei.vkr.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import mpei.vkr.databinding.FragmentLogin2Binding


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
        lifecycle.addObserver(loginViewModel)
        return binding.root
    }
}
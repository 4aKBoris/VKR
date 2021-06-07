@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.masterkey.change

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import mpei.vkr.R
import mpei.vkr.databinding.FragmentLogin2Binding
import mpei.vkr.databinding.OldMasterKeyFragmentBinding
import mpei.vkr.ui.login.LoginViewModel2

class OldMasterKeyFragment : Fragment() {

    private var _binding: OldMasterKeyFragmentBinding? = null
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(OldMasterKeyViewModel::class.java)
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OldMasterKeyFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

}
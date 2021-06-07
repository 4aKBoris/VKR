@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.masterkey.change

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.pathMasterKey
import mpei.vkr.R
import mpei.vkr.databinding.FragmentLoginBinding
import mpei.vkr.databinding.NewMasterKeyFragmentBinding
import mpei.vkr.ui.login.LoginViewModel
import java.io.File

class NewMasterKeyFragment : Fragment() {

    private var _binding: NewMasterKeyFragmentBinding? = null
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(NewMasterKeyViewModel::class.java)
    }

    private val binding get() = _binding!!

    private lateinit var masterKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            masterKey = it.getString(ARG_MASTER_KEY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewMasterKeyFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        viewModel.masterKey = masterKey
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
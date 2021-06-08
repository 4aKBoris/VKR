package mpei.vkr.ui.choice.certificate

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mpei.vkr.R
import mpei.vkr.databinding.CertificateFragmentBinding
import mpei.vkr.databinding.CreateCertificateFragmentBinding

class CreateCertificateFragment : Fragment() {

    private var _binding: CreateCertificateFragmentBinding? = null
    private lateinit var viewModel: CreateCertificateViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateCertificateFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateCertificateViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
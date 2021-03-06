package mpei.vkr.ui.choice.certificate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
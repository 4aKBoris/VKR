package mpei.vkr.ui.settings.items.signature

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import mpei.vkr.R
import mpei.vkr.databinding.SettingsMessageDigestFragmentBinding
import mpei.vkr.databinding.SettingsSignatureFragmentBinding
import mpei.vkr.ui.settings.items.message.digest.SettingsMessageDigestViewModel

class SettingsSignatureFragment : Fragment() {

    private var _binding: SettingsSignatureFragmentBinding? = null
    private lateinit var viewModel: SettingsSignatureViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsSignatureFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SettingsSignatureViewModel::class.java)
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
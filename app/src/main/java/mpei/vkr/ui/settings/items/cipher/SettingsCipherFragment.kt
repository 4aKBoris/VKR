@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.cipher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mpei.vkr.Crypto.Algorithms
import mpei.vkr.databinding.SettingsCipherFragmentBinding

class SettingsCipherFragment : Fragment() {

    private var _binding: SettingsCipherFragmentBinding? = null
    private lateinit var viewModel: SettingsCipherViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsCipherFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SettingsCipherViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.seekBarCount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.cipherCount.value = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.seekBarKeySize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val algorithm = viewModel.cipherAlgorithm.value!!
                viewModel.keySize.value = alg.getKeySizeMin(algorithm) + alg.getKeySizeStep(algorithm) * progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val alg = Algorithms()
    }
}
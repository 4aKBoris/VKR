package mpei.vkr.ui.settings.items.message.digest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mpei.vkr.databinding.SettingsMessageDigestFragmentBinding

class SettingsMessageDigestFragment : Fragment() {

    private var _binding: SettingsMessageDigestFragmentBinding? = null
    private lateinit var viewModel: SettingsMessageDigestViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsMessageDigestFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SettingsMessageDigestViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.seekBarCount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.hashCount.value = progress
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
}
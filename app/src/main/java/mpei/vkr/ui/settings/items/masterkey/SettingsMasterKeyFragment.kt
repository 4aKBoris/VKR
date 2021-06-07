@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.masterkey

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import mpei.vkr.databinding.SettingsMasterKeyFragmentBinding
import mpei.vkr.ui.settings.items.ModelFactory


class SettingsMasterKeyFragment : Fragment() {

    private var _binding: SettingsMasterKeyFragmentBinding? = null
    private lateinit var viewModel: SettingsMasterKeyViewModel
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsMasterKeyFragmentBinding.inflate(inflater, container, false)
        val sp = PreferenceManager.getDefaultSharedPreferences(binding.view.context)
        viewModel = ViewModelProviders.of(this, ModelFactory(sp))[SettingsMasterKeyViewModel::class.java]
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.seekBarLength.min = 1

        binding.seekBarLength.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.length.value = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveSettings()
    }
}
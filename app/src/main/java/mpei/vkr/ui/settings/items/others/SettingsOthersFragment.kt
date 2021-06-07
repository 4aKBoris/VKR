@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.others

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import mpei.vkr.databinding.SettingsOthersFragmentBinding
import mpei.vkr.ui.settings.items.ModelFactory

class SettingsOthersFragment : Fragment() {

    private var _binding: SettingsOthersFragmentBinding? = null
    private lateinit var viewModel: SettingsOthersViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsOthersFragmentBinding.inflate(inflater, container, false)
        val sp = PreferenceManager.getDefaultSharedPreferences(binding.root.context)
        viewModel = ViewModelProviders.of(this, ModelFactory(sp))[SettingsOthersViewModel::class.java]
        binding.viewmodel = viewModel
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
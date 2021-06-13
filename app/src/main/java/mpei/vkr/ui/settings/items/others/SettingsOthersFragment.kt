@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings.items.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mpei.vkr.databinding.SettingsOthersFragmentBinding

class SettingsOthersFragment : Fragment() {

    private var _binding: SettingsOthersFragmentBinding? = null
    private lateinit var viewModel: SettingsOthersViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsOthersFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SettingsOthersViewModel::class.java)
        binding.viewmodel = viewModel
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
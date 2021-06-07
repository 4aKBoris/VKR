@file:Suppress("DEPRECATION")

package mpei.vkr.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.databinding.SettingsFragmentBinding

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            println(it.getString(ARG_MASTER_KEY))
        }
    }

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private val tabNames = arrayOf(
        "Шифр",
        "Хэш-функция",
        "Цифровая подпись",
        "Мастер-ключ",
        "Другие"
    )

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (arguments != null) {
            // The getPrivacyPolicyLink() method will be created automatically.
            val url: String = requireArguments().getString("MasterKey")!!
            println(url)
        }
        else println("54321")
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        val adapter = this.activity?.let { SettingsAdapter(it) }
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
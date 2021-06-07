package mpei.vkr.ui.settings.items.cipher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mpei.vkr.R

class SettingsCipherFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsCipherFragment()
    }

    private lateinit var viewModel: SettingsCipherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_cipher_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsCipherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
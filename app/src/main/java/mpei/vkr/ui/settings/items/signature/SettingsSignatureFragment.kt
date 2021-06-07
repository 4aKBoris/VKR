package mpei.vkr.ui.settings.items.signature

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mpei.vkr.R

class SettingsSignatureFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsSignatureFragment()
    }

    private lateinit var viewModel: SettingsSignatureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_signature_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsSignatureViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
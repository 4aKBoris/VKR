package mpei.vkr.ui.settings.items.message.digest

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mpei.vkr.R

class SettingsMessageDigestFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsMessageDigestFragment()
    }

    private lateinit var viewModel: SettingsMessageDigestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_message_digest_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsMessageDigestViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
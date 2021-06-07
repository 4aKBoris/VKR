package mpei.vkr.ui.settings.items.others

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mpei.vkr.R

class SettingsOthersFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsOthersFragment()
    }

    private lateinit var viewModel: SettingsOthersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_others_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsOthersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
package mpei.vkr.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mpei.vkr.R

class SettingsItemFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsItemFragment()
    }

    private lateinit var viewModel: SettingsItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
package mpei.vkr.ui.encrypt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mpei.vkr.databinding.FragmentEncryptBinding

class EncryptFragment : Fragment() {

    private lateinit var encryptViewModel: EncryptViewModel
    private var _binding: FragmentEncryptBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        encryptViewModel =
            ViewModelProvider(this).get(EncryptViewModel::class.java)

        _binding = FragmentEncryptBinding.inflate(inflater, container, false)

        //val textView: TextView = binding.textView2
        /*encryptViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
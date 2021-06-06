package mpei.vkr.ui.decrypt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import mpei.vkr.databinding.FragmentDecryptBinding

class DecryptFragment : Fragment() {

    private var fileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fileName = it.getString("fileName")
        }
    }

    private lateinit var decryptViewModel: DecryptViewModel
    private var _binding: FragmentDecryptBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        decryptViewModel =
            ViewModelProvider(this).get(DecryptViewModel::class.java)
        _binding = FragmentDecryptBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = decryptViewModel
        if (fileName != null) decryptViewModel.fileName.value = fileName
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
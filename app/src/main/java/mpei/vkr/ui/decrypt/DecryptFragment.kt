package mpei.vkr.ui.decrypt

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mpei.vkr.Constants.FileName
import mpei.vkr.databinding.FragmentDecryptBinding

class DecryptFragment : Fragment() {

    private var fileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fileName = it.getString(FileName)
        }
    }

    private lateinit var decryptViewModel: DecryptViewModel
    private var _binding: FragmentDecryptBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("UseRequireInsteadOfGet")
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
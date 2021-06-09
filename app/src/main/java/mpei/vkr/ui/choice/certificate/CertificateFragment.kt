@file:Suppress("DEPRECATION")

package mpei.vkr.ui.choice.certificate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mpei.vkr.databinding.CertificateFragmentBinding

class CertificateFragment : Fragment() {

    private var _binding: CertificateFragmentBinding? = null
    private lateinit var viewModel: CertificateViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CertificateFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CertificateViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.imageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "file/*"
            startActivityForResult(intent, PICK_FILE_RESULT_CODE)
        }
        binding.buttonImport.setOnClickListener { viewModel.import() }
        binding.buttonExport.setOnClickListener { viewModel.export(binding.root.context) }
        binding.floatingActionButton.setOnClickListener { viewModel.createCertificate(this) }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_FILE_RESULT_CODE -> if (resultCode == Activity.RESULT_OK) viewModel.pathCertificate.value =
                data!!.data!!.path!!.removePrefix("/root")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PICK_FILE_RESULT_CODE = 1
    }
}
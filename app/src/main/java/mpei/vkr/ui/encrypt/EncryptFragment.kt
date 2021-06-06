@file:Suppress("DEPRECATION")

package mpei.vkr.ui.encrypt

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import mpei.vkr.databinding.FragmentEncryptBinding


class EncryptFragment : Fragment() {

    private val encryptViewModel by lazy {
        ViewModelProviders.of(this).get(EncryptViewModel::class.java)
    }
    private var _binding: FragmentEncryptBinding? = null

    private val binding get() = _binding!!

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEncryptBinding.inflate(inflater, container, false)
        binding.viewmodel = encryptViewModel
        binding.lifecycleOwner = this
        binding.buttonFileName.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "file/*"
            startActivityForResult(intent, PICK_FILE_RESULT_CODE)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_FILE_RESULT_CODE -> if (resultCode == RESULT_OK) encryptViewModel.fileName.value = data!!.data!!.path!!.substring(5)
        }
    }

    companion object {
        private const val PICK_FILE_RESULT_CODE = 1
    }
}
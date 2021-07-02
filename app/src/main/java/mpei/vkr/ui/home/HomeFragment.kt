@file:Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package mpei.vkr.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.LOG_TAG
import mpei.vkr.Others.FileClass
import mpei.vkr.databinding.FragmentHomeBinding
import java.io.File


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var masterKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            masterKey = it.getString(ARG_MASTER_KEY)
        }
    }

    private var notificationManager: NotificationManager? = null

    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        notificationManager =
            requireActivity().applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.button2.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "file/*"
            startActivityForResult(intent, PICK_FILE_RESULT_CODE)
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_FILE_RESULT_CODE -> if (resultCode == Activity.RESULT_OK) {
                val content_describer: Uri? = data!!.data
                val src: String? = content_describer!!.path
                val source = File(src)
                //Log.d("src is ", source.)
                val filename: String? = content_describer.lastPathSegment
                Log.d(LOG_TAG, filename!!)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PICK_FILE_RESULT_CODE = 1
        private val file = FileClass()
    }
}
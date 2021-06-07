@file:Suppress("DEPRECATION")

package mpei.vkr.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.pathMasterKey
import mpei.vkr.Others.FileReadWrite
import mpei.vkr.R
import mpei.vkr.databinding.FragmentHomeBinding
import java.io.File
import java.util.jar.Manifest

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

    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        println(Environment.getExternalStorageDirectory().path)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        binding.button2.setOnClickListener {
            GlobalScope.launch {
                binding.textHome.text = "dwadwa"
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
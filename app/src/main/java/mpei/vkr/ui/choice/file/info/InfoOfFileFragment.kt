package mpei.vkr.ui.choice.file.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mpei.vkr.Constants.FileName
import mpei.vkr.databinding.FragmentFileInfoListBinding
import mpei.vkr.ui.choice.file.info.placeholder.FileInfoClass

class InfoOfFileFragment : Fragment() {

    private lateinit var fileName: String

    private var _binding: FragmentFileInfoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fileName = it.getString(FileName)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileInfoListBinding.inflate(inflater, container, false)
        binding.list.layoutManager = LinearLayoutManager(context)
        GlobalScope.launch(Dispatchers.Main) {
            binding.list.adapter =
                MyInfoOfFileRecyclerViewAdapter(FileInfoClass(fileName).getInfo(binding.root.context))
        }
        return binding.root
    }
}
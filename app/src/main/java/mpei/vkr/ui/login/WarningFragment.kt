package mpei.vkr.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mpei.vkr.R

class WarningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_warning, container, false)
        textViewWarning = view.findViewById(R.id.textViewWarning)
        return view
    }

    fun Visible(warning: String) {
        textViewWarning.text = warning
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var textViewWarning: TextView
    }
}
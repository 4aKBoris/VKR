@file:Suppress("UNREACHABLE_CODE")

package mpei.vkr.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentContainerView
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Exception.MyException
import mpei.vkr.MainActivity
import mpei.vkr.R


class LoginFragment2 : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_2, container, false)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        buttonOk = view.findViewById(R.id.buttonConf)
        fragmentContainerView = view.findViewById(R.id.fragmentContainerView)
        checkBoxVisible = view.findViewById(R.id.checkBoxVisible_2)

        buttonOk.setOnClickListener {
            fragmentContainerView.visibility = View.INVISIBLE
            try {
                val password = editTextPassword.text.toString()
                val mk = MasterKey(password)
                if (password.isEmpty()) throw MyException("Введите мастер ключ!")
                if (!mk.IsCorrect()) throw MyException("Мастер ключ введён неверно!")
                val intent = Intent(view.context, MainActivity::class.java)
                intent.putExtra(ARG_MASTER_KEY, password)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } catch (e: MyException) {
                fragmentContainerView.visibility = View.VISIBLE
                warningFragment.Visible(e.message!!)
                @Suppress("DEPRECATION")
                Handler().postDelayed({
                    fragmentContainerView.visibility = View.INVISIBLE
                }, 5000)
            } catch (e: Exception) {
                fragmentContainerView.visibility = View.VISIBLE
                warningFragment.Visible("Мастер ключ введён неверно!")
                @Suppress("DEPRECATION")
                Handler().postDelayed({
                    fragmentContainerView.visibility = View.INVISIBLE
                }, 5000)
            }
        }

        checkBoxVisible.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) editTextPassword.transformationMethod = null
            else editTextPassword.transformationMethod = PasswordTransformationMethod()
            editTextPassword.setSelection(editTextPassword.text.toString().length)
        }

        return view
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var editTextPassword: EditText

        @SuppressLint("StaticFieldLeak")
        private lateinit var buttonOk: Button

        @SuppressLint("StaticFieldLeak")
        private val warningFragment = WarningFragment()

        @SuppressLint("StaticFieldLeak")
        private lateinit var checkBoxVisible: ToggleButton

        private lateinit var fragmentContainerView: FragmentContainerView
    }
}
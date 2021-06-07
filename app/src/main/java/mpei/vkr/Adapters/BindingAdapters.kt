@file:Suppress("PackageName")

package mpei.vkr.Adapters

import android.annotation.SuppressLint
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import mpei.vkr.ui.encrypt.EncryptViewModel
import java.io.File

@BindingAdapter("app:visibility")
fun changeVisibility(editText: EditText, type: Boolean) {
    if (!type) editText.transformationMethod = PasswordTransformationMethod()
    else editText.transformationMethod = null
    if (editText.isFocused) editText.setSelection(editText.text.toString().length)
}
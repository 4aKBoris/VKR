@file:Suppress("PackageName")

package mpei.vkr.Adapters

import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibility")
fun changeVisibility(editText: EditText, type: Boolean) {
    if (!type) editText.transformationMethod = PasswordTransformationMethod()
    else editText.transformationMethod = null
    if (editText.isFocused) editText.setSelection(editText.text.toString().length)
}

@BindingAdapter("app:progressbar")
fun visibilityProgressBar(view: View, type: Boolean) {
    if (type) view.visibility = View.VISIBLE
    else view.visibility = View.INVISIBLE
}
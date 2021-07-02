package mpei.vkr.Others

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ToastShow {

    fun show(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    suspend fun suspendShow(context: Context, message: String) =
        withContext(Dispatchers.Main) { show(context, message) }
}
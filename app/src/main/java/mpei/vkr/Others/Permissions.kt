@file:Suppress("PackageName")

package mpei.vkr.Others

import android.Manifest
import android.app.Activity
import androidx.core.app.ActivityCompat

class Permissions {
    fun requestMultiplePermissions(activity : Activity, PERMISSION_REQUEST_CODE : Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        ),
            PERMISSION_REQUEST_CODE)
    }
}
@file:Suppress("DEPRECATION")

package mpei.vkr.Others

import android.os.Environment
import mpei.vkr.Constants.path
import java.io.File

class Folder {

    init {
        if (!File(path + VKR).exists()) {
            createDir(VKR)
            createDir(CipherFiles)
            createDir(ClearFiles)
            createDir(Certificates)
        }
    }

    private fun createDir(name: String): File? {
        val baseDir: File?
        baseDir = File(path)
        val folder = File(baseDir, name)
        if (folder.exists()) return folder
        if (folder.isFile) folder.delete()
        return if (folder.mkdirs()) folder else Environment.getExternalStorageDirectory()
    }

    companion object {
        private const val VKR = "VKR"
        private const val CipherFiles = "VKR/CipherFiles"
        private const val ClearFiles = "VKR/ClearFiles"
        private const val Certificates = "VKR/Certificates"
    }
}
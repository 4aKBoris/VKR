@file:Suppress("PackageName")

package mpei.vkr.Others

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mpei.vkr.Exception.MyException
import java.io.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.jvm.Throws

class FileClass {

    suspend fun writeFile(fileName: String, arr: ByteArray) = withContext(Dispatchers.IO) {
        val bw = BufferedOutputStream(FileOutputStream(File(fileName)))
        bw.write(arr)
        bw.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun copyFile(path1: String, path2: String) = withContext(Dispatchers.IO) {
        Files.copy(
            File(path1).toPath(),
            File(path2).toPath(),
            StandardCopyOption.REPLACE_EXISTING
        )
    }!!

    @Throws(MyException::class)
    suspend fun readFile(fileName: String): ByteArray = withContext(Dispatchers.IO) {
        BufferedInputStream(FileInputStream(File(fileName))).readBytes()
    }

    fun readFileNotSuspend(fileName: String) = BufferedInputStream(FileInputStream(File(fileName))).readBytes()

    fun fileSize(fileName: String): String {
        return if (!File(fileName).exists()) ""
        else {
            when (val size = File(fileName).length()) {
                in 0..999 -> "$size Байт"
                in 1000..999999 -> String.format(rule, size.toDouble() / 1000).plus(" КБ")
                in 1000000..999999999 -> String.format(rule, size.toDouble() / 1000000).plus(" МБ")
                in 1000000000..999999999999 -> String.format(rule, size.toDouble() / 1000000000)
                    .plus(" ГБ")
                else -> String.format(rule, size.toDouble() / 1000000000000).plus(" ТБ")
            }
        }
    }

    fun fileName(path: String): String = File(path).name

    companion object {
        private const val rule = "%.2f"
    }
}
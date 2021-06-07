@file:Suppress("PackageName")

package mpei.vkr.Others

import mpei.vkr.ui.decrypt.DecryptViewModel
import java.io.*

class FileReadWrite(private val fileName: String) {

    fun writeFile(arr: ByteArray) {
        val bw = BufferedOutputStream(FileOutputStream(File(fileName)))
        bw.write(arr)
        bw.close()
    }

    fun readFile(): ByteArray =
        BufferedInputStream(FileInputStream(File(fileName))).readBytes()

    fun readFileOne(): Int =
        BufferedInputStream(FileInputStream(File(fileName))).read()

    fun readFileN(n: Int): ByteArray {
        val br = BufferedInputStream(FileInputStream(File(fileName)))
        val arr = ByteArray(n)
        br.read(arr, 0, n)
        return arr
    }

    fun fileSize(): String {
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

    companion object {
        private const val rule = "%.2f"
    }
}
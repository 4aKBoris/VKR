@file:Suppress("PackageName")

package mpei.vkr.Others

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
}
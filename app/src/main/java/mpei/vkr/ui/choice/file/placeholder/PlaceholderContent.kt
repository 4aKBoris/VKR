@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package mpei.vkr.ui.choice.file.placeholder

import mpei.vkr.Constants.Crypto
import mpei.vkr.Constants.path
import mpei.vkr.Constants.pathToCipherFiles
import mpei.vkr.Others.FileClass
import java.io.File
import java.util.ArrayList
import java.util.HashMap

object PlaceholderContent {

    val ITEMS: MutableList<FileItem> = ArrayList()

    val ITEM_MAP: MutableMap<String, FileItem> = HashMap()

    private val file = FileClass()

    init {
        File(pathToCipherFiles).listFiles().filter { it.isFile }
            .forEachIndexed { index, file -> addFile(createFileItem(index, file.name)) }
    }

    private fun addFile(item: FileItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createFileItem(i: Int, fileName: String): FileItem {
        return FileItem(i.toString(), fileName, fileType(fileName), file.fileSize(pathToCipherFiles + fileName))
    }

    private fun fileType(fileName: String): String {
        val type = fileName.removeSuffix(Crypto)
        return if (type.find { it == '.' } == null) "Без типа" else type.substring(
            type.lastIndexOf(
                '.'
            ) + 1
        )
    }

    data class FileItem(
        val id: String,
        val fileName: String,
        val type: String,
        val fileSize: String
    ) {
        override fun toString(): String = fileName
    }
}
package mpei.vkr.ui.choice.file.info.placeholder

import android.content.Context
import kotlinx.coroutines.*
import mpei.vkr.Constants.pathToCipherFiles
import mpei.vkr.Crypto.MetaData
import mpei.vkr.Exception.MyException
import mpei.vkr.Others.FileClass
import mpei.vkr.Others.ToastShow
import kotlin.coroutines.CoroutineContext

class FileInfoClass(private val fileName: String): CoroutineScope {

    private val ITEMS = mutableListOf<Item>()

    suspend fun getInfo(context: Context): List<Item> {
        coroutineScope {
            launch(Dispatchers.Default) {
                val filePath = pathToCipherFiles + fileName
                try {
                    val arr = file.readFileFirstBytes(filePath, 5000)
                    val meta = MetaData(arr)
                    val metaData = meta.getMetaData()
                    ITEMS.add(Item("Алгоритм шифрования", metaData.cipherAlgorithm))
                    ITEMS.add(Item("Длина ключа шифрования", "${metaData.keySize} байт"))
                    ITEMS.add(Item("Количество итераций", metaData.cipherCount.toString()))
                    ITEMS.add(Item("Алгоритм цифровой подписи", metaData.signatureAlgorithm))
                    ITEMS.add(Item("Алгоритм хэш-функции", metaData.hashAlgorithm ?: "-"))
                    ITEMS.add(Item("Количество итераций", metaData.hashCount?.toString() ?: "-"))
                    ITEMS.add(Item("Использование случайной примеси", getProperty(metaData.salt)))
                } catch (e: MyException) {
                    toast.suspendShow(context, e.message!!)
                } finally {
                    ITEMS.add(0, Item("Размер файла", file.fileSize(filePath)))
                    ITEMS.add(0, Item("Имя файла", fileName))
                }
            }
        }
        return ITEMS
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    companion object {
        private val job = SupervisorJob()
        private val file = FileClass()
        private val toast = ToastShow()

        private fun getProperty(property: ByteArray?): String = if (property == null) "Нет" else "Да"
    }

    data class Item(val name: String, val info: String) {
        override fun toString(): String = name + info
    }
}
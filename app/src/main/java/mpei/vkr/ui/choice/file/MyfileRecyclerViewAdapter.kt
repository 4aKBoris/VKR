@file:Suppress("DEPRECATION")

package mpei.vkr.ui.choice.file

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import mpei.vkr.Constants.FileName
import mpei.vkr.Constants.LOG_TAG
import mpei.vkr.R
import mpei.vkr.databinding.FragmentFileBinding
import mpei.vkr.ui.choice.file.placeholder.PlaceholderContent


class MyfileRecyclerViewAdapter(
    private val values: List<PlaceholderContent.FileItem>, private val fragment: FileFragment
) : RecyclerView.Adapter<MyfileRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentFileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.fileNameView.text = item.fileName
        if (item.type in mapTypeFile.keys) holder.fileTypeView.setImageResource(mapTypeFile[item.type]!!)
        else holder.fileTypeView.setImageResource(mapTypeFile["file"]!!)
        Log.d(LOG_TAG, item.type)
        holder.fileSizeView.text = item.fileSize
    }

    override fun getItemCount(): Int = values.size


    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener { choiceFile(it.context, values[position].fileName) }
    }

    private fun action(id: Int, fileName: String) {
        findNavController(fragment).navigate(id, Bundle().apply { putString(FileName, fileName) })
    }

    private fun choiceFile(context: Context, fileName: String) {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> action(
                        R.id.action_fileFragment_to_nav_decrypt,
                        fileName
                    )
                    DialogInterface.BUTTON_NEGATIVE -> dialog.cancel()
                    DialogInterface.BUTTON_NEUTRAL -> action(
                        R.id.action_fileFragment_to_infoOfFileFragment,
                        fileName
                    )
                }
            }
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Выбор действия с файлом $fileName?")
            .setPositiveButton("Выбрать", dialogClickListener)
            .setNeutralButton("Информация", dialogClickListener)
            .setNegativeButton("Отмена", dialogClickListener).show()
    }

    inner class ViewHolder(binding: FragmentFileBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val fileNameView: TextView = binding.fileName
        val fileTypeView: ImageView = binding.fileType
        val fileSizeView: TextView = binding.fileSize

        override fun toString(): String {
            return super.toString() + " '" + fileNameView.text + "'"
        }
    }

    companion object {
        private val mapTypeFile = mapOf(
            Pair("avi", R.drawable.avi),
            Pair("bin", R.drawable.bin),
            Pair("css", R.drawable.css),
            Pair("csv", R.drawable.csv),
            Pair("dbf", R.drawable.dbf),
            Pair("doc", R.drawable.doc),
            Pair("exe", R.drawable.exe),
            Pair("file", R.drawable.file),
            Pair("gif", R.drawable.gif),
            Pair("ico", R.drawable.ico),
            Pair("jar", R.drawable.jar),
            Pair("jpg", R.drawable.jpg),
            Pair("js", R.drawable.js),
            Pair("mkv", R.drawable.mkv),
            Pair("mov", R.drawable.mov),
            Pair("mp3", R.drawable.mp3),
            Pair("mp4", R.drawable.mp4),
            Pair("pdf", R.drawable.pdf),
            Pair("png", R.drawable.png),
            Pair("ppt", R.drawable.ppt),
            Pair("psd", R.drawable.psd),
            Pair("rtf", R.drawable.rtf),
            Pair("txt", R.drawable.txt),
            Pair("waw", R.drawable.waw),
            Pair("wmv", R.drawable.wmv),
            Pair("xml", R.drawable.xml),
            Pair("xls", R.drawable.xls),
            Pair("zip", R.drawable.zip)
        )
    }
}
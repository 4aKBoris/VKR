@file:Suppress("DEPRECATION")

package mpei.vkr.ui.choice.file

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
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
        holder.fileTypeView.text = item.type
        holder.fileSizeView.text = item.fileSize
    }

    override fun getItemCount(): Int = values.size


    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener { choiceFile(it.context, values[position].fileName) }
    }

    private fun action(id: Int, fileName: String) {
        findNavController(fragment).navigate(id, Bundle().apply { putString("fileName", fileName) })
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
                        R.id.action_fileFragment_to_fileInfoFragment,
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
        val fileTypeView: TextView = binding.fileType
        val fileSizeView: TextView = binding.fileSize

        override fun toString(): String {
            return super.toString() + " '" + fileNameView.text + "'"
        }
    }

}
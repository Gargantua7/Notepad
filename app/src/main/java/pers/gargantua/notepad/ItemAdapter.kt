package pers.gargantua.notepad

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import java.util.*

/**
 * @author Gargantua丶
 **/
class ItemAdapter(private val itemList: ArrayList<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val content: TextView = view.findViewById(R.id.content)
    }

    lateinit var main: MainActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        itemList[position].run {
            holder.title.text = this.title
            holder.content.text = this.content
            holder.itemView.setOnClickListener {
                main.startActivityForResult(Intent(main, Editor::class.java).apply {
                    putExtra("id", this@run.id)
                    putExtra("title", this@run.title)
                    putExtra("content", this@run.content)
                }, 1)
            }
            holder.itemView.setOnLongClickListener {
                AlertDialog.Builder(main).apply {
                    setMessage("确定删除？")
                    setNegativeButton("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    setPositiveButton("确定") { dialog, _ ->
                        Room.databaseBuilder(
                            context,
                            MyDatabase::class.java, "notepad"
                        ).allowMainThreadQueries().build().getItemDao().delete(this@run)
                        remove(position)
                        dialog.dismiss()
                    }
                }.show()
                true
            }
        }

    override fun getItemCount(): Int = itemList.size

    fun add(item: Item) {
        itemList.add(item)
        notifyItemInserted(itemCount)
        notifyDataSetChanged()
    }

    private fun remove(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }
}
package pers.gargantua.notepad

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var itemList: ArrayList<Item>

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = getColor(R.color.colorPrimary)

        listInit()

        fab.setOnClickListener {
            startActivityForResult(Intent(this, Editor::class.java), 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0, 1 -> if (resultCode == RESULT_OK) {
                val item = Item(
                    title = data?.getStringExtra("title").toString(),
                    content = data?.getStringExtra("content").toString()
                )
                data?.getIntExtra("id", 0)?.let { item.id = it }
                Room.databaseBuilder(
                    applicationContext,
                    MyDatabase::class.java, "notepad"
                ).allowMainThreadQueries().build().getItemDao().apply {
                    when (requestCode) {
                        0 -> {
                            insert(item)
                            itemAdapter.add(item)
                        }
                        1 -> {
                            update(item)
                            for (i in itemList) {
                                if (i.id == item.id) itemList.apply { set(this.indexOf(i), item) }
                            }
                            itemAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun listInit() {
        itemList = ArrayList(
            Room.databaseBuilder(
                applicationContext,
                MyDatabase::class.java, "notepad"
            ).allowMainThreadQueries().build().getItemDao().queryAll().asList()
        )
        recycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            itemAdapter = ItemAdapter(itemList)
            adapter = itemAdapter
        }
        itemAdapter.main = this
    }
}

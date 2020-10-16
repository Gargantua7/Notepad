package pers.gargantua.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_editor.*

class Editor : AppCompatActivity() {

    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        window.statusBarColor = getColor(R.color.colorPrimary)

        id = intent.getIntExtra("id", 0)
        if (id > 0) {
            title = intent.getStringExtra("title")
            titleEdit.setText(title)
            contentEdit.setText(intent.getStringExtra("content"))
        } else {
            id = Room.databaseBuilder(
                applicationContext,
                MyDatabase::class.java, "notepad"
            ).allowMainThreadQueries().build().getItemDao().queryMaxId() + 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.submit, menu)
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.done -> {
                val title = titleEdit.text.toString()
                val content = contentEdit.text.toString()
                if (title != "" || content != "") {
                    setResult(RESULT_OK, Intent().apply {
                        putExtra("id", id)
                        putExtra("title", title)
                        putExtra("content", content)
                    })
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
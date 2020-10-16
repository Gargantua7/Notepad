package pers.gargantua.notepad

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Gargantua丶
 **/
@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var content: String
)
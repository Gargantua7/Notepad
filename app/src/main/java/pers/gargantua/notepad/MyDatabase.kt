package pers.gargantua.notepad

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Gargantua丶
 **/
@Database(entities = [Item::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getItemDao(): ItemDao
}
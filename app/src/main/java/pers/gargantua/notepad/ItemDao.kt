package pers.gargantua.notepad

import androidx.room.*

/**
 * @author Gargantua丶
 **/
@Dao
interface ItemDao {

    @Query("SELECT * FROM item")
    fun queryAll(): Array<Item>

    @Query("SELECT * FROM item WHERE id = :id")
    fun queryById(id: Int): Item

    @Query("SELECT max(id) FROM item")
    fun queryMaxId(): Int

    @Insert
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)
}


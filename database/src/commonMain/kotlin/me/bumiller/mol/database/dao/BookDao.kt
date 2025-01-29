package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.BookEntity

/**
 * Class that manages querying of [BookEntity].
 */
@Dao
interface BookDao : SimpleDao<BookEntity> {

    @Query("SELECT * FROM books")
    override fun getAll(): Flow<List<BookEntity>>

    @Query("DELETE FROM books WHERE books.id = :id")
    override suspend fun delete(id: Long)

    @Insert
    override suspend fun insert(entity: BookEntity): Long

    @Update
    override suspend fun update(entity: BookEntity): Int

}
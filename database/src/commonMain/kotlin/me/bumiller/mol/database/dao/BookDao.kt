package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Delete
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

    @Delete
    override suspend fun delete(vararg entity: BookEntity)

    @Insert
    override suspend fun insert(vararg entity: BookEntity): Long

    @Update
    override suspend fun update(vararg entity: BookEntity): Int

}
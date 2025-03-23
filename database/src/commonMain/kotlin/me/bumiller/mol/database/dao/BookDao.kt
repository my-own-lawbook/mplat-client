package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Query
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

}
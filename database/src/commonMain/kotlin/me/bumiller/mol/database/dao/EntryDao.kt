package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.EntryEntity

/**
 * Class that manages querying of [EntryEntity].
 */
@Dao
interface EntryDao : SimpleDao<EntryEntity> {

    @Query("SELECT * FROM entries")
    override fun getAll(): Flow<List<EntryEntity>>
}
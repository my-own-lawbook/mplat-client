package me.bumiller.civoris.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.bumiller.civoris.database.dao.base.SimpleDao
import me.bumiller.civoris.database.entities.EntryEntity

/**
 * Class that manages querying of [EntryEntity].
 */
@Dao
interface EntryDao : SimpleDao<EntryEntity> {

    @Query("SELECT * FROM entries")
    override fun getAll(): Flow<List<EntryEntity>>
}
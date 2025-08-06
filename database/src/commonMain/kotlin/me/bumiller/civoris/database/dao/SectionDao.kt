package me.bumiller.civoris.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.bumiller.civoris.database.dao.base.SimpleDao
import me.bumiller.civoris.database.entities.SectionEntity

/**
 * Class that manages querying of [SectionEntity].
 */
@Dao
interface SectionDao : SimpleDao<SectionEntity> {

    @Query("SELECT * FROM sections")
    override fun getAll(): Flow<List<SectionEntity>>
}
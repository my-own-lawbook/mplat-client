package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.SectionEntity

/**
 * Class that manages querying of [SectionEntity].
 */
@Dao
interface SectionDao : SimpleDao<SectionEntity> {

    @Query("SELECT * FROM sections")
    override fun getAll(): Flow<List<SectionEntity>>
}
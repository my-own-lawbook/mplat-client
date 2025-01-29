package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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

    @Query("DELETE FROM sections WHERE sections.id = :id")
    override suspend fun delete(id: Long)

    @Insert
    override suspend fun insert(entity: SectionEntity): Long

    @Update
    override suspend fun update(entity: SectionEntity): Int
}
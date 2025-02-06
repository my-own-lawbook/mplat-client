package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.ForeignUserEntity

/**
 * Class that manages querying of [ForeignUserEntity].
 */
@Dao
interface ForeignUserDao : SimpleDao<ForeignUserEntity> {

    @Query("SELECT * FROM foreign_users")
    override fun getAll(): Flow<List<ForeignUserEntity>>

    @Delete
    override suspend fun delete(vararg entity: ForeignUserEntity)

    @Insert
    override suspend fun insert(entity: ForeignUserEntity): Long

    @Update
    override suspend fun update(entity: ForeignUserEntity): Int
}
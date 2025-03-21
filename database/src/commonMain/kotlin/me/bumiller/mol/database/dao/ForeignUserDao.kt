package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Query
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
}
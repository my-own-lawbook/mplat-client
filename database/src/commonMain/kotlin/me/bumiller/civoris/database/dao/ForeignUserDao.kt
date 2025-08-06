package me.bumiller.civoris.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.bumiller.civoris.database.dao.base.SimpleDao
import me.bumiller.civoris.database.entities.ForeignUserEntity

/**
 * Class that manages querying of [ForeignUserEntity].
 */
@Dao
interface ForeignUserDao : SimpleDao<ForeignUserEntity> {

    @Query("SELECT * FROM foreign_users")
    override fun getAll(): Flow<List<ForeignUserEntity>>
}
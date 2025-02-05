package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.InvitationEntity

/**
 * Class that manages querying of [InvitationEntity].
 */
@Dao
interface InvitationDao : SimpleDao<InvitationEntity> {

    @Query("SELECT * FROM invitations")
    override fun getAll(): Flow<List<InvitationEntity>>

    @Delete
    override suspend fun delete(vararg entity: InvitationEntity)

    @Insert
    override suspend fun insert(vararg entity: InvitationEntity): Long

    @Update
    override suspend fun update(vararg entity: InvitationEntity): Int
}
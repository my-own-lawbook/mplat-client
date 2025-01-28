package me.bumiller.mol.database.dao

import androidx.room.Dao
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

    @Query("DELETE FROM invitations WHERE invitations.id = :id")
    override suspend fun delete(id: Long): InvitationEntity?

    @Insert
    override suspend fun insert(entity: InvitationEntity): Long

    @Update
    override suspend fun update(entity: InvitationEntity): Int
}
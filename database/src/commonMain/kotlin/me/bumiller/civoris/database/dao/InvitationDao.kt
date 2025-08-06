package me.bumiller.civoris.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.bumiller.civoris.database.dao.base.SimpleDao
import me.bumiller.civoris.database.entities.InvitationEntity

/**
 * Class that manages querying of [InvitationEntity].
 */
@Dao
interface InvitationDao : SimpleDao<InvitationEntity> {

    @Query("SELECT * FROM invitations")
    override fun getAll(): Flow<List<InvitationEntity>>
}
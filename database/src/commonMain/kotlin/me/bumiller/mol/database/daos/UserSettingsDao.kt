package me.bumiller.mol.database.daos

import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.entities.UserSettingsEntity

/**
 * DAO to access the user settings
 */
interface UserSettingsDao {

    /**
     * Updates or inserts a [UserSettingsEntity]
     *
     * @param settings The entity
     * @return The created or updated entity
     */
    @Upsert
    suspend fun upsert(settings: UserSettingsEntity): UserSettingsEntity

    /**
     * Gets the one saved user settings entity, or null if none exists
     *
     * @return The entity, or null if it was not yet created
     */
    @Query(
        """
        SELECT * FROM user_settings LIMIT 1
    """
    )
    fun get(): Flow<UserSettingsEntity?>

}
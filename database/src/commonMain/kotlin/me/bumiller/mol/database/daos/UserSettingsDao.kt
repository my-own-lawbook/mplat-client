package me.bumiller.mol.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.entities.UserSettingsEntity

/**
 * DAO to access the user settings
 */
@Dao
interface UserSettingsDao {

    /**
     * Updates a [UserSettingsEntity]
     *
     * @param settings The entity
     * @return The number of updated columns
     */
    @Update
    suspend fun update(settings: UserSettingsEntity): Int

    /**
     * Inserts a [UserSettingsEntity]
     *
     * @param settings The new entity
     * @return The saved entity
     */
    @Insert
    suspend fun insert(settings: UserSettingsEntity): Long

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
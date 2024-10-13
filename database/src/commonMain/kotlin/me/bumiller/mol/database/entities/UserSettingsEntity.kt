package me.bumiller.mol.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity that represents the settings the user set.
 *
 * This table should only ever contain one row. This is for simplicity so no other data store needs to be implemented
 */
@Entity(
    tableName = "user_settings"
)
data class UserSettingsEntity(

    /**
     * The id of the entity.
     *
     * Does not really matter, as only one row will ever be present
     */
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * The url to the backend server
     */
    val backendUrl: String?,

    /**
     * The color mode.
     *
     * Possible values are:
     * - dark
     * - light
     * - system
     */
    val colorMode: String,

    /**
     * The color scheme.
     *
     * Possible values are:
     * - dynamic
     * - system
     */
    val colorScheme: String

)

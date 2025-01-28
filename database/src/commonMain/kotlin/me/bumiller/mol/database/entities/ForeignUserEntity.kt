package me.bumiller.mol.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import me.bumiller.mol.database.entities.base.SimpleEntity

/**
 * An entity resembling one record in the 'foreign_users' table.
 */
@Entity(
    tableName = "foreign_users"
)
data class ForeignUserEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    /**
     * The username of the user.
     */
    val username: String,

    /**
     * The first name of the user.
     */
    val firstName: String,

    /**
     * The last name of the user.
     */
    val lastName: String,

    /**
     * The gender of the user. One of the following:
     * - male
     * - female
     * - not_say
     * - other
     */
    val gender: String,

    /**
     * The birthday of the user
     */
    val birthday: LocalDateTime

) : SimpleEntity

package me.bumiller.civoris.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.bumiller.civoris.database.entities.base.SimpleEntity

/**
 * Entity that resembles one record in the 'entries' table.
 */
@Entity(
    tableName = "entries"
)
data class EntryEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    /**
     * The id of the parent [BookEntity].
     */
    val parentBookId: Long,

    /**
     * Shorthand key for the section,
     */
    val key: String,

    /**
     * The name of the section
     */
    val name: String

) : SimpleEntity

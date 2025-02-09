package me.bumiller.mol.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.bumiller.mol.database.entities.base.SimpleEntity

/**
 * Entity that models one record in the 'sections' table.
 */
@Entity(
    tableName = "sections"
)
data class SectionEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    /**
     * Id of the parent [EntryEntity].
     */
    val parentEntryId: Long,

    /**
     * Index resembling a position inside the entry.
     */
    val index: String,

    /**
     * The name of the section.
     */
    val name: String,

    /**
     * The content of the section.
     */
    val content: String

) : SimpleEntity

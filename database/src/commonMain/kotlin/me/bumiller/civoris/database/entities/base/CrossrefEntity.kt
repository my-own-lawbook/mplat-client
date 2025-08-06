package me.bumiller.civoris.database.entities.base

/**
 * Base class for any entity that symbolizes a row in a crossref sql table.
 */
interface CrossrefEntity : BaseEntity {

    /**
     * The id of the parent entity.
     */
    val parentId: Long

    /**
     * The id of the child entity.
     */
    val childId: Long

}
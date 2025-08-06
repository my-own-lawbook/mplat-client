package me.bumiller.civoris.database.entities.base

/**
 * Base class for any class that models one record in an sql table.
 */
interface SimpleEntity : BaseEntity {

    /**
     * The id of the entity.
     */
    val id: Long

}
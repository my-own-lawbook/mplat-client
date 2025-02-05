package me.bumiller.mol.database.dao.base

import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.entities.base.BaseEntity
import me.bumiller.mol.database.entities.base.SimpleEntity

/**
 * Base class for any dao responsible for querying entities of type [BaseEntity].
 */
interface BaseDao<Entity : BaseEntity> {

    /**
     * Gets all entities stored in the table.
     *
     * @return The list of all entities
     */
    fun getAll(): Flow<List<Entity>>

    /**
     * Inserts an entity into the table.
     *
     * [SimpleEntity.id] is ignored.
     *
     * @param entity The entity
     * @return The new rowId of the entity
     */
    suspend fun insert(vararg entity: Entity): Long

    /**
     * Updates an existing entity.
     *
     * @param entity The entity to update, identified by the [SimpleEntity.id] field
     * @return The number of updated columns
     */
    suspend fun update(vararg entity: Entity): Int

    /**
     * Deletes an entity by the specified id.
     *
     * @param entity The entity to delete
     */
    suspend fun delete(vararg entity: Entity)

}
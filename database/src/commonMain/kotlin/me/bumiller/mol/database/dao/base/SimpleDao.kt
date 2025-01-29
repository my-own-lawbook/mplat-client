package me.bumiller.mol.database.dao.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.bumiller.mol.database.entities.base.SimpleEntity

/**
 * Base class for any dao responsible for querying entities of type [SimpleEntity].
 */
interface SimpleDao<Entity : SimpleEntity> {

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
     * @return The new [SimpleEntity.id] of the entity
     */
    suspend fun insert(entity: Entity): Long

    /**
     * Updates an existing entity.
     *
     * @param entity The entity to update, identified by the [SimpleEntity.id] field
     * @return The number of updated columns
     */
    suspend fun update(entity: Entity): Int

    /**
     * Deletes an entity by the specified id.
     *
     * @param id The id of the entity
     * @return The deleted entity
     */
    suspend fun delete(id: Long)

    /**
     * Gets a specific entity by the id.
     *
     * @param id The id of the entity
     * @return A flow with the entity, or null if not found
     */
    fun getById(id: Long): Flow<Entity?> =
        getAll().map { entities -> entities.firstOrNull { it.id == id } }

    /**
     * Gets all entities with specific ids.
     *
     * @param ids All ids to filter for
     * @return The list of all entities having ids in [ids]. Non present ids are ignored.
     */
    fun getByIds(ids: List<Long>): Flow<List<Entity>> =
        getAll().map { entities -> entities.filter { it.id in ids } }

}
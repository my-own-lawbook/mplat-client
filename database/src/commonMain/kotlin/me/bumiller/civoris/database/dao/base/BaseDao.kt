package me.bumiller.civoris.database.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.bumiller.civoris.database.entities.base.BaseEntity
import me.bumiller.civoris.database.entities.base.SimpleEntity

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
    @Insert
    suspend fun insert(entity: Entity): Long

    /**
     * Updates an existing entity.
     *
     * @param entity The entity to update, identified by the [SimpleEntity.id] field
     * @return The number of updated columns
     */
    @Update
    suspend fun update(entity: Entity): Int

    /**
     * Deletes an entity.
     *
     * @param entity The entity to delete
     */
    @Delete
    suspend fun delete(entity: Entity)

}
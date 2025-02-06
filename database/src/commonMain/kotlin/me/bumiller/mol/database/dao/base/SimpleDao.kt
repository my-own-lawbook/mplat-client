package me.bumiller.mol.database.dao.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.bumiller.mol.database.entities.base.SimpleEntity

/**
 * Base class for any dao responsible for querying entities of type [SimpleEntity].
 */
interface SimpleDao<Entity : SimpleEntity> : BaseDao<Entity> {


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
        getAll().map { entities ->
            entities.filter { it.id in ids }
        }

}
package me.bumiller.civoris.database.dao.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.bumiller.civoris.database.entities.base.CrossrefEntity

/**
 * Base class for any dao that provides access to a [CrossrefEntity].
 */
interface CrossrefDao<Entity : CrossrefEntity> : BaseDao<Entity> {

    /**
     * Gets all cross references for a parent.
     *
     * @param parentId The id of the parent
     * @return A flow of a list of all the children id's for the parent, or null if not found
     */
    fun getAllForParent(parentId: Long): Flow<List<Long>?> =
        getAll().map { entities ->
            entities.filter { it.parentId == parentId }.map(CrossrefEntity::childId)
        }

    /**
     * Gets all cross references for a child.
     *
     * @param childId The id of the child
     * @return A flow of a list of all the parent id's for the child, or null if not found
     */
    fun getAllForChild(childId: Long): Flow<List<Long>?> =
        getAll().map { entities ->
            entities.filter { it.childId == childId }.map(CrossrefEntity::parentId)
        }

}
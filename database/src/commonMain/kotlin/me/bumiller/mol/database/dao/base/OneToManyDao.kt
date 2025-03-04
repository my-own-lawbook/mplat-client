package me.bumiller.mol.database.dao.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import me.bumiller.mol.database.entities.base.SimpleEntity

/**
 * Base class for any dao that helps to access the relation between two entities, where one acts as
 * a parent and the other as a child on a one-to-many relationship.
 *
 * @param Parent The parent entity, can be referenced by multiple children
 * @param Child The child entity, can reference only one parent
 */
class OneToManyDao<Parent : SimpleEntity, Child : SimpleEntity>(
    private val parentDao: SimpleDao<Parent>,
    private val childDao: SimpleDao<Child>,
    private val parentId: (Child) -> Long
) {

    /**
     * Gets the parent entity for a given child.
     *
     * @param child The child for which to get the parent
     * @return A flow with the parent
     */
    fun getParent(child: Child): Flow<Parent?> {
        return parentDao.getById(parentId(child))
    }

    /**
     * Gets the parent entity for a given child's id.
     *
     * @param childId The id of child for which to get the parent
     * @return A flow with the parent
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getParent(childId: Long): Flow<Parent?> {
        return childDao.getById(childId).flatMapLatest { child ->
            if (child == null) flowOf(null)
            else getParent(child)
        }
    }

    /**
     * Gets the children for a specific parent.
     *
     * @param parent The parent for which to get the children
     * @return A flow of the children
     */
    fun getChildren(parent: Parent): Flow<List<Child>?> {
        return getChildren(parent.id)
    }

    /**
     * Gets the children for a specific parent's id.
     *
     * @param parentId The id of parent for which to get the children
     * @return A flow of the children
     */
    fun getChildren(parentId: Long): Flow<List<Child>?> {
        return childDao.getAll().map { children ->
            children.filter { child ->
                parentId(child) == parentId
            }
        }
    }

}
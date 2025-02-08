package me.bumiller.mol.database.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.bumiller.mol.database.dao.base.CrossrefDao
import me.bumiller.mol.database.entities.base.CrossrefEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError

/**
 * Base class for any test that tests instances of a [CrossrefDao] for a specific [CrossrefEntity].
 */
abstract class CrossrefDaoTest<Entity : CrossrefEntity, Dao : CrossrefDao<Entity>> :
    BaseDaoTest<Entity, Dao>() {

    override fun createEntity(key: Long): Entity = createEntity(key, key)

    /**
     * Creates a new crossref entity.
     *
     * @param parentId The id of the parent
     * @param childId The id of the child
     * @return The crossref entity
     */
    abstract fun createEntity(parentId: Long, childId: Long): Entity

    @Test
    @DisplayName("Getting all children by a specific parent returns only children from that parent.")
    fun getAllForParentWorks() = runTest {
        (2L..10L).map { parentId ->
            (1L..<parentId).map { childId ->
                createEntity(parentId, childId)
            }
        }
            .flatten()
            .forEach { dao.insert(it) }

        (2L..10L).forEach { parentId ->
            val children = dao.getAllForParent(parentId).first() ?: emptyList()
            Assertions.assertArrayEquals(
                (1..<parentId).map { it.toInt() }.toIntArray(),
                children.map { it.toInt() }.toIntArray()
            )
        }
    }

    @Test
    @DisplayName("Getting all parents by a specific child returns only parents from that child.")
    fun getAllForChildrenWorks() = runTest {
        (2L..10L).map { childId ->
            (1L..<childId).map { parentId ->
                createEntity(parentId, childId)
            }
        }
            .flatten()
            .forEach { dao.insert(it) }

        (2L..10L).forEach { childId ->
            val children = dao.getAllForChild(childId).first() ?: throw AssertionFailedError()
            Assertions.assertArrayEquals(
                (1..<childId).map { it.toInt() }.toIntArray(),
                children.map { it.toInt() }.toIntArray()
            )
        }
    }

}
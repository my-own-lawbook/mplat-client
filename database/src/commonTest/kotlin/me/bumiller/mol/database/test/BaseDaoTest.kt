package me.bumiller.mol.database.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.bumiller.mol.database.dao.base.BaseDao
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.base.BaseEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Base class for any dao that tests an instance of a [BaseDao] for a specific [BaseEntity].
 */
abstract class BaseDaoTest<Entity : BaseEntity, Dao : BaseDao<Entity>> : BaseRoomTest() {

    /**
     * Creates an entity with unique attributes.
     *
     * @param key The unique key
     * @return A created entity
     */
    abstract fun createEntity(key: Long): Entity

    /**
     * Performs an update on an entity.
     *
     * @return An updated entity with the same id but different content.
     */
    abstract fun Entity.performUpdate(): Entity

    /**
     * The dao to test against.
     */
    abstract val dao: Dao

    /**
     * Tests whether the [SimpleDao.insert] implementation works.
     */
    @Test
    @DisplayName("Inserting an entity allows retrieving that entity.")
    fun insertWorks() = runTest {
        (1..10).map { it * 250L }
            .map(::createEntity)
            .forEach { dao.insert(it) }

        val entities = dao.getAll().first()

        Assertions.assertEquals(10, entities.size)
    }

    /**
     * Tests whether the [SimpleDao.getAll] implementation works.
     */
    @Test
    @DisplayName("Getting all entities returns all entities.")
    fun getAllWorks() = runTest {
        val entities = (1..10).map { it * 250L }
            .map(::createEntity)
            .onEach { dao.insert(it) }

        val allEntities = dao.getAll().first()

        Assertions.assertTrue(entities.containsAll(allEntities))
        Assertions.assertTrue(allEntities.containsAll(entities))
    }

    /**
     * Tests whether the [SimpleDao.delete] implementation works.
     */
    @Test
    @DisplayName("Deleting an entity removes it from the database.")
    fun deleteWorks() = runTest {
        val deletedEntities = (1..10).map { it * 250L }
            .map { it to createEntity(it) }
            .onEach { (_, entity) -> dao.insert(entity) }
            .filter { (key, _) -> key in listOf(2L, 6L, 9L) }
            .onEach { (_, entity) -> dao.delete(entity) }
            .map(Pair<Long, Entity>::second)

        val remainingEntities = dao.getAll().first()

        remainingEntities.forEach { entity ->
            Assertions.assertFalse(entity in deletedEntities)
        }
        deletedEntities.forEach { entity ->
            Assertions.assertFalse(entity in remainingEntities)
        }
    }

    /**
     * Tests whether the [SimpleDao.update] implementation works.
     */
    @Test
    @DisplayName("Updating an entity changes the specified properties.")
    fun updateWorks() = runTest {
        (1..10).map { it * 250L }
            .map(::createEntity)
            .forEach { entity ->
                dao.insert(entity)

                val updated = entity.performUpdate()
                dao.update(updated)

                val all = dao.getAll().first()
                Assertions.assertTrue(updated in all)
                Assertions.assertFalse(entity in all)
            }
    }

}
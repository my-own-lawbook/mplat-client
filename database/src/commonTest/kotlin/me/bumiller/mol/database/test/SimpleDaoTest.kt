package me.bumiller.mol.database.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.base.SimpleEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Base class for any dao that tests an instance of a [SimpleDao] for a specific [SimpleEntity].
 */
abstract class SimpleDaoTest<Entity : SimpleEntity, Dao : SimpleDao<Entity>> : BaseRoomTest() {

    /**
     * Creates an entity with unique attributes.
     *
     * @param key The unique key
     * @return A created entity
     */
    abstract fun createEntity(key: Long): Entity

    /**
     * Copies an entity with regards to the id.
     *
     * @param id The new id
     * @return The copied entity with the id of [id]
     */
    abstract fun Entity.copyId(id: Long): Entity

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
     * Tests whether the [SimpleDao.getById] implementation works.
     */
    @Test
    @DisplayName("Getting an entity by id returns only the specific entity.")
    fun getByIdWorks() = runTest {
        val entities = (1..10).map { it * 250L }
            .map(::createEntity)
            .map { it to dao.insert(it) }
            .map { (entity, id) -> entity.copyId(id) }

        entities.forEach { entity ->
            val retrievedEntity = dao.getById(entity.id).first()

            Assertions.assertEquals(entity, retrievedEntity)
        }
    }

    /**
     * Tests whether the [SimpleDao.getByIds] implementation works.
     */
    @Test
    @DisplayName("Getting multiple entities by id's returns only entities with those ids and ignores invalid id's.")
    fun getByIdsWorks() = runTest {
        (1..10).map { it * 250L }
            .map(::createEntity)
            .forEach { it to dao.insert(it) }

        listOf(listOf(2L, 6L, 9L, 14L), listOf(), listOf(33L, 1L), listOf(5L, 7L))
            .map { ids -> ids.map { it * 250L } }
            .forEach { ids ->
                val entitiesForIds = dao.getByIds(ids).first()

                ids.forEach { id ->
                    if (id in (1..10).map { it * 250L }) {
                        Assertions.assertEquals(1, entitiesForIds.count { it.id == id })
                    } else {
                        Assertions.assertTrue(entitiesForIds.none { it.id == id })
                    }
                }
            }
    }

    /**
     * Tests whether the [SimpleDao.getAll] implementation works.
     */
    @Test
    @DisplayName("Getting all entities returns all entities.")
    fun getAllWorks() = runTest {
        val entities = (1..10).map { it * 250L }
            .map(::createEntity)
            .map { it to dao.insert(it) }
            .map { (entity, id) -> entity.copyId(id) }

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
        (1..10).map { it * 250L }
            .map(::createEntity)
            .forEach { it to dao.insert(it) }

        val deletedEntities = listOf(1L, 8L, 4L)
            .let { dao.getByIds(it).first() }
            .onEach { entity ->
                dao.delete(entity)
            }

        deletedEntities.forEach { entity ->
            val retrieved = dao.getById(entity.id)
            Assertions.assertNull(retrieved)
        }
    }

    /**
     * Tests whether the [SimpleDao.update] implementation works.
     */
    @Test
    @DisplayName("Updating an entity changes the specified properties.")
    fun updateWorks() = runTest {
        val entities = (1..10).map { it * 250L }
            .map(::createEntity)
            .map { it to dao.insert(it) }
            .map { (entity, id) -> entity.copyId(id) }

        val updatedEntities = entities
            .map { it.performUpdate() }
            .onEach { dao.update(it) }

        entities.forEach { entity ->
            val retrieved = dao.getById(entity.id).first()
            val updated = updatedEntities.find { it.id == entity.id }

            Assertions.assertEquals(updated, retrieved)
        }
    }

}
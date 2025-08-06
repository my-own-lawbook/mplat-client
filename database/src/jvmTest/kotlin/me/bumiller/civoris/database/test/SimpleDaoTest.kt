package me.bumiller.civoris.database.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.bumiller.civoris.database.dao.base.SimpleDao
import me.bumiller.civoris.database.entities.base.SimpleEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Base class for any dao that tests an instance of a [SimpleDao] for a specific [SimpleEntity].
 */
abstract class SimpleDaoTest<Entity : SimpleEntity, Dao : SimpleDao<Entity>> :
    BaseDaoTest<Entity, Dao>() {

    /**
     * Copies an entity with regards to the id.
     *
     * @param id The new id
     * @return The copied entity with the id of [id]
     */
    abstract fun Entity.copyId(id: Long): Entity

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

}
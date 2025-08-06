package me.bumiller.civoris.data.test

import io.mockk.every
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.bumiller.civoris.data.repository.DatabaseSimpleRepository
import me.bumiller.civoris.database.dao.base.SimpleDao
import me.bumiller.civoris.database.entities.base.SimpleEntity
import me.bumiller.civoris.model.Identifiable
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Base class for any test class that tests a [DatabaseSimpleRepository]
 */
abstract class DatabaseSimpleRepositoryTest<Model : Identifiable<Long>, Entity : SimpleEntity, Dao : SimpleDao<Entity>, Repository : DatabaseSimpleRepository<Model, Entity, Dao>> :
    BaseDataTest() {

    /**
     * Creates the to test repository from a dao.
     *
     * @param dao The mocked dao
     * @return The repository to test
     */
    abstract fun createRepository(dao: Dao): Repository

    /**
     * Creates the mock. This is needed because [Dao] is not a reified parameter.
     */
    abstract fun createMock(): Dao

    abstract fun createEntity(key: Long): Entity

    /**
     * The mocked dao.
     */
    private lateinit var dao: Dao

    /**
     * The repository backed by the mocked dao.
     */
    private lateinit var repository: Repository

    @BeforeEach
    fun setup() {
        dao = createMock()
        repository = createRepository(dao)
    }

    @Test
    @DisplayName("Get all returns all entities from the dao")
    fun getAllWorks() = runTest {
        every { dao.getAll() } returns flowOf(emptyList())

        val allEmpty = repository.getAll().first()

        (1L..10L)
            .map(::createEntity)
            .let { every { dao.getAll() } returns flowOf(it) }

        val allFilled = repository.getAll().first()

        Assertions.assertEquals(0, allEmpty.size)
        Assertions.assertEquals(10, allFilled.size)
    }

    @Test
    @DisplayName("Get all by id returns only the specified id")
    fun getAllByIdWorks() = runTest {
        (1L..10L)
            .map(::createEntity)
            .forEach { entity -> every { dao.getById(entity.id) } returns flowOf(entity) }
        (11L..15L)
            .forEach { every { dao.getById(it) } returns flowOf(null) }

        (1L..10L)
            .forEach { id ->
                val forId = repository.getById(id).first()
                Assertions.assertNotNull(forId)
            }

        (11L..15L)
            .forEach { id ->
                val forId = repository.getById(id).first()
                Assertions.assertNull(forId)
            }
    }

}
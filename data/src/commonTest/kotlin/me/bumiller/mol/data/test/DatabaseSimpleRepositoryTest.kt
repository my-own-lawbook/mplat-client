package me.bumiller.mol.data.test

import io.mockk.every
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import me.bumiller.mol.data.mapping.mapBookModel
import me.bumiller.mol.data.mapping.mapEntryModel
import me.bumiller.mol.data.mapping.mapForeignUserModel
import me.bumiller.mol.data.mapping.mapInvitationModel
import me.bumiller.mol.data.mapping.mapSectionModel
import me.bumiller.mol.data.repository.DatabaseSimpleRepository
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.database.entities.SectionEntity
import me.bumiller.mol.database.entities.base.SimpleEntity
import me.bumiller.mol.model.Identifiable
import me.bumiller.mol.model.law.ForeignUser
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.law.LawBookInvitation
import me.bumiller.mol.model.law.LawEntry
import me.bumiller.mol.model.law.LawSection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Base class for any test class that tests a [DatabaseSimpleRepository]
 */
abstract class DatabaseSimpleRepositoryTest<Model : Identifiable<Long>, Entity : SimpleEntity, Dao : SimpleDao<Entity>, Repository : DatabaseSimpleRepository<Model, Entity, Dao>> {

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
    internal lateinit var dao: Dao

    /**
     * The repository backed by the mocked dao.
     */
    internal lateinit var repository: Repository

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

    /**
     * Creates a [BookEntity].
     *
     * @param key The key for uniqueness
     * @return The [BookEntity]
     */
    fun bookEntity(key: Long) = BookEntity(
        id = key,
        key = "key $key",
        name = "name $key",
        description = "description $key",
        isFavourite = key % 2L == 0L
    )

    /**
     * Creates a [LawBook].
     *
     * @param key The key for uniqueness
     * @return The [LawBook]
     */
    fun bookModel(key: Long) = mapBookModel(bookEntity(key))

    /**
     * Creates a [EntryEntity].
     *
     * @param key The key for uniqueness
     * @return The [EntryEntity]
     */
    fun entryEntity(key: Long) = EntryEntity(
        id = key,
        parentBookId = key,
        key = "key $key",
        name = "name $key"
    )

    /**
     * Creates a [LawEntry].
     *
     * @param key The key for uniqueness
     * @return The [LawEntry]
     */
    fun entryModel(key: Long) = mapEntryModel(entryEntity(key))

    /**
     * Creates a [ForeignUserEntity].
     *
     * @param key The key for uniqueness
     * @return The [ForeignUserEntity]
     */
    fun foreignUserEntity(key: Long) = ForeignUserEntity(
        id = key,
        username = "username $key",
        firstName = "firstName $key",
        lastName = "lastName $key",
        gender = modOptions(key, listOf("male", "female", "not_say", "other")),
        birthday = LocalDate.fromEpochDays(1000 + key.toInt())
    )

    /**
     * Creates a [ForeignUser].
     *
     * @param key The key for uniqueness
     * @return The [ForeignUser]
     */
    fun foreignUser(key: Long) = mapForeignUserModel(foreignUserEntity(key))

    /**
     * Creates an [InvitationEntity].
     *
     * @param key The key for uniqueness
     * @return The [InvitationEntity]
     */
    fun invitationEntity(key: Long) = InvitationEntity(
        id = key,
        authorId = key,
        recipientId = key,
        targetId = key,
        role = modOptions(key, listOf("member", "moderator", "admin")),
        sentTimestamp = Instant.fromEpochSeconds(1000 + key),
        usedTimestamp = Instant.fromEpochSeconds(1000 + key),
        expiredTimestamp = Instant.fromEpochSeconds(1000 + key),
        status = modOptions(key, listOf("open", "accepted", "declined", "revoked")),
        message = "message $key"
    )

    /**
     * Creates a [LawBookInvitation].
     *
     * @param key The key for uniqueness
     * @return The [LawBookInvitation]
     */
    fun invitationModel(key: Long) = mapInvitationModel(invitationEntity(key))

    /**
     * Creates a [SectionEntity].
     *
     * @param key The key for uniqueness
     * @return The [SectionEntity]
     */
    fun sectionEntity(key: Long) = SectionEntity(
        id = key,
        parentEntryId = key,
        index = "index $key",
        name = "name $key",
        content = "content $key"
    )

    /**
     * Creates a [LawSection].
     *
     * @param key The key for uniqueness
     * @return The [LawSection]
     */
    fun sectionModel(key: Long) = mapSectionModel(sectionEntity(key))

    private fun <Data> modOptions(key: Long, list: List<Data>) = list[(key % list.size).toInt()]

}
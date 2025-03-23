package me.bumiller.mol.database.test

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import me.bumiller.mol.database.MolDatabase
import me.bumiller.mol.database.dao.BookDao
import me.bumiller.mol.database.dao.BookMemberCrossrefDao
import me.bumiller.mol.database.dao.EntryDao
import me.bumiller.mol.database.dao.ForeignUserDao
import me.bumiller.mol.database.dao.InvitationDao
import me.bumiller.mol.database.dao.SectionDao
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.database.entities.SectionEntity
import org.junit.jupiter.api.BeforeEach

/**
 * Sets up the database and daos
 */
abstract class BaseRoomTest {

    /**
     * The in-memory database
     */
    private lateinit var db: MolDatabase

    @BeforeEach
    fun setup() {
        db = Room.inMemoryDatabaseBuilder<MolDatabase>()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    internal val bookDao: BookDao
        get() = db.bookDao()
    internal val entryDao: EntryDao
        get() = db.entryDao()
    internal val foreignUserDao: ForeignUserDao
        get() = db.foreignUserDao()
    internal val invitationDao: InvitationDao
        get() = db.invitationDao()
    internal val sectionDao: SectionDao
        get() = db.sectionDao()
    internal val bookMemberDao: BookMemberCrossrefDao
        get() = db.bookMemberCrossrefDao()

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
        isFavourite = key % 2L == 0L,
        isMember = key % 2L == 1L
    )

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
        gender = "gender $key",
        birthday = LocalDate.fromEpochDays(1000 + key.toInt())
    )

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
        role = "role $key",
        sentTimestamp = Instant.fromEpochSeconds(1000 + key),
        usedTimestamp = Instant.fromEpochSeconds(1000 + key),
        expiredTimestamp = Instant.fromEpochSeconds(1000 + key),
        status = "status $key",
        message = "message $key"
    )

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

}
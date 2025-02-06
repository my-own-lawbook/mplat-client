package me.bumiller.mol.database.dao

import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.test.SimpleDaoTest
import org.junit.jupiter.api.Test

/**
 * Tests for the [EntryDao].
 */
class EntryDaoTest : SimpleDaoTest<EntryEntity, EntryDao>() {

    override val dao: EntryDao
        get() = entryDao

    override fun createEntity(key: Long) = entryEntity(key)

    override fun EntryEntity.performUpdate() = copy(
        key = "key updated",
        name = "name updated"
    )

    override fun EntryEntity.copyId(id: Long) = copy(id = id)

    @Test
    fun triggerParentTests() {
    }
}
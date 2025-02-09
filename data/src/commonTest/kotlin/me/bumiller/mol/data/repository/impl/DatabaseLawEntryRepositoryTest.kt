package me.bumiller.mol.data.repository.impl

import io.mockk.mockk
import me.bumiller.mol.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.mol.database.dao.EntryDao
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.model.law.LawEntry
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class DatabaseLawEntryRepositoryTest :
    DatabaseSimpleRepositoryTest<LawEntry, EntryEntity, EntryDao, DatabaseLawEntryRepository>() {

    override fun createRepository(dao: EntryDao) = DatabaseLawEntryRepository(dao)

    override fun createMock() = mockk<EntryDao>()

    override fun createEntity(key: Long) = entryEntity(key)

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
    }
}
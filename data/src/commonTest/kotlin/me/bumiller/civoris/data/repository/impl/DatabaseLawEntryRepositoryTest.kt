package me.bumiller.civoris.data.repository.impl

import io.mockk.mockk
import me.bumiller.civoris.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.civoris.database.dao.EntryDao
import me.bumiller.civoris.database.entities.EntryEntity
import me.bumiller.civoris.model.law.LawEntry
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
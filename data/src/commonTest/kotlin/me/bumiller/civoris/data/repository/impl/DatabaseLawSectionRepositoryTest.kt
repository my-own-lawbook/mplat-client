package me.bumiller.civoris.data.repository.impl

import io.mockk.mockk
import me.bumiller.civoris.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.civoris.database.dao.SectionDao
import me.bumiller.civoris.database.entities.SectionEntity
import me.bumiller.civoris.model.law.LawSection
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class DatabaseLawSectionRepositoryTest :
    DatabaseSimpleRepositoryTest<LawSection, SectionEntity, SectionDao, DatabaseLawSectionRepository>() {

    override fun createRepository(dao: SectionDao) = DatabaseLawSectionRepository(dao)

    override fun createMock() = mockk<SectionDao>()

    override fun createEntity(key: Long) = sectionEntity(key)

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
    }
}
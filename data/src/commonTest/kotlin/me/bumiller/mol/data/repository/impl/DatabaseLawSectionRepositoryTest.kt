package me.bumiller.mol.data.repository.impl

import io.mockk.mockk
import me.bumiller.mol.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.mol.database.dao.SectionDao
import me.bumiller.mol.database.entities.SectionEntity
import me.bumiller.mol.model.law.LawSection
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
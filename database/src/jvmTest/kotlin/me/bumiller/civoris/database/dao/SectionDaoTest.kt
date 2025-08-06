package me.bumiller.civoris.database.dao

import me.bumiller.civoris.database.entities.SectionEntity
import me.bumiller.civoris.database.test.SimpleDaoTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Tests for the [SectionDao].
 */
class SectionDaoTest : SimpleDaoTest<SectionEntity, SectionDao>() {

    override val dao: SectionDao
        get() = sectionDao

    override fun createEntity(key: Long) = sectionEntity(key)

    override fun SectionEntity.performUpdate() = copy(
        name = "name updated",
        index = "index updated",
        content = "content updated"
    )

    override fun SectionEntity.copyId(id: Long) = copy(id = id)

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
        // Stub method to trigger implicit tests from parent class
    }
}
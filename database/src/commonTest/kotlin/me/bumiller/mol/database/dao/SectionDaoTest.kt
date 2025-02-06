package me.bumiller.mol.database.dao

import me.bumiller.mol.database.entities.SectionEntity
import me.bumiller.mol.database.test.SimpleDaoTest
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
    fun triggerParentTests() {
    }
}
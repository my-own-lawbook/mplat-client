package me.bumiller.civoris.database.dao

import me.bumiller.civoris.database.entities.ForeignUserEntity
import me.bumiller.civoris.database.test.SimpleDaoTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Tests for the [ForeignUserDao].
 */
class ForeignUserDaoTest : SimpleDaoTest<ForeignUserEntity, ForeignUserDao>() {

    override val dao: ForeignUserDao
        get() = foreignUserDao

    override fun createEntity(key: Long) = foreignUserEntity(key)

    override fun ForeignUserEntity.performUpdate() = copy(
        username = "username updated",
        firstName = "firstName updated",
        lastName = "lastName updated",
        gender = "gender updated"
    )

    override fun ForeignUserEntity.copyId(id: Long) = copy(id = id)

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
    }
}
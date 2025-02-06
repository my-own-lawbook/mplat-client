package me.bumiller.mol.database.dao

import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.database.test.SimpleDaoTest
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
    fun triggerParentTests() {
    }
}
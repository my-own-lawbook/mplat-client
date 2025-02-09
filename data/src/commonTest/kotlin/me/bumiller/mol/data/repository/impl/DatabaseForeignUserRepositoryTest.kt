package me.bumiller.mol.data.repository.impl

import io.mockk.mockk
import me.bumiller.mol.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.mol.database.dao.ForeignUserDao
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.model.law.ForeignUser
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class DatabaseForeignUserRepositoryTest :
    DatabaseSimpleRepositoryTest<ForeignUser, ForeignUserEntity, ForeignUserDao, DatabaseForeignUserRepository>() {

    override fun createRepository(dao: ForeignUserDao) = DatabaseForeignUserRepository(dao)

    override fun createMock() = mockk<ForeignUserDao>()

    override fun createEntity(key: Long) = foreignUserEntity(key)

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
    }
}
package me.bumiller.mol.data.repository.impl

import io.mockk.mockk
import me.bumiller.mol.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.mol.database.dao.InvitationDao
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.model.law.LawBookInvitation
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class DatabaseLawBookInvitationRepositoryTest :
    DatabaseSimpleRepositoryTest<LawBookInvitation, InvitationEntity, InvitationDao, DatabaseBookInvitationRepository>() {

    override fun createRepository(dao: InvitationDao) = DatabaseBookInvitationRepository(dao)

    override fun createMock() = mockk<InvitationDao>()

    override fun createEntity(key: Long) = invitationEntity(key)

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
    }
}
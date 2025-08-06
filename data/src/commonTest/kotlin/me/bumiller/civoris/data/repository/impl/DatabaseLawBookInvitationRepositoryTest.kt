package me.bumiller.civoris.data.repository.impl

import io.mockk.mockk
import me.bumiller.civoris.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.civoris.database.dao.InvitationDao
import me.bumiller.civoris.database.entities.InvitationEntity
import me.bumiller.civoris.model.law.LawBookInvitation
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
        // Stub method to trigger implicit tests from parent class
    }
}
package me.bumiller.civoris.database.dao

import me.bumiller.civoris.database.entities.InvitationEntity
import me.bumiller.civoris.database.test.SimpleDaoTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Tests for the [InvitationDao].
 */
class InvitationDaoTest : SimpleDaoTest<InvitationEntity, InvitationDao>() {

    override val dao: InvitationDao
        get() = invitationDao

    override fun createEntity(key: Long) = invitationEntity(key)

    override fun InvitationEntity.performUpdate() = copy(
        role = "role updated",
        status = "status updated",
        message = "message updated"
    )

    override fun InvitationEntity.copyId(id: Long) = copy(id = id)

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
    }
}
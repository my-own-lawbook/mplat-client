package me.bumiller.mol.database.dao

import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.database.test.SimpleDaoTest
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
    fun triggerParentTests() {
    }
}
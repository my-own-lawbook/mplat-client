package me.bumiller.mol.data.repository.impl

import me.bumiller.mol.data.mapping.mapInvitationModel
import me.bumiller.mol.data.repository.DatabaseSimpleRepository
import me.bumiller.mol.data.repository.LawBookInvitationRepository
import me.bumiller.mol.database.dao.InvitationDao
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.model.law.LawBookInvitation

internal class DatabaseBookInvitationRepository(dao: InvitationDao) : LawBookInvitationRepository,
    DatabaseSimpleRepository<LawBookInvitation, InvitationEntity, InvitationDao>(dao) {

    override suspend fun createModelFor(entity: InvitationEntity) = mapInvitationModel(entity)

}
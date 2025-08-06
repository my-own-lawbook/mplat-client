package me.bumiller.civoris.data.repository.impl

import me.bumiller.civoris.data.mapping.mapInvitationModel
import me.bumiller.civoris.data.repository.DatabaseSimpleRepository
import me.bumiller.civoris.data.repository.LawBookInvitationRepository
import me.bumiller.civoris.database.dao.InvitationDao
import me.bumiller.civoris.database.entities.InvitationEntity
import me.bumiller.civoris.model.law.LawBookInvitation

internal class DatabaseBookInvitationRepository(dao: InvitationDao) : LawBookInvitationRepository,
    DatabaseSimpleRepository<LawBookInvitation, InvitationEntity, InvitationDao>(dao) {

    override suspend fun createModelFor(entity: InvitationEntity) = mapInvitationModel(entity)

}
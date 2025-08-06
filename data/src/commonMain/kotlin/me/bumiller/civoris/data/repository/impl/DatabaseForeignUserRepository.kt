package me.bumiller.civoris.data.repository.impl

import me.bumiller.civoris.data.mapping.mapForeignUserModel
import me.bumiller.civoris.data.repository.DatabaseSimpleRepository
import me.bumiller.civoris.data.repository.ForeignUserRepository
import me.bumiller.civoris.database.dao.ForeignUserDao
import me.bumiller.civoris.database.entities.ForeignUserEntity
import me.bumiller.civoris.model.law.ForeignUser

internal class DatabaseForeignUserRepository(dao: ForeignUserDao) : ForeignUserRepository,
    DatabaseSimpleRepository<ForeignUser, ForeignUserEntity, ForeignUserDao>(dao) {

    override suspend fun createModelFor(entity: ForeignUserEntity) = mapForeignUserModel(entity)

}
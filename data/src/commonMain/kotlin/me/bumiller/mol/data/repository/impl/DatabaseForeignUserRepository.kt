package me.bumiller.mol.data.repository.impl

import me.bumiller.mol.data.mapping.foreignUserModel
import me.bumiller.mol.data.repository.DatabaseSimpleRepository
import me.bumiller.mol.data.repository.ForeignUserRepository
import me.bumiller.mol.database.dao.ForeignUserDao
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.model.law.ForeignUser

internal class DatabaseForeignUserRepository(dao: ForeignUserDao) : ForeignUserRepository,
    DatabaseSimpleRepository<ForeignUser, ForeignUserEntity, ForeignUserDao>(dao) {

    override suspend fun createModelFor(entity: ForeignUserEntity) = foreignUserModel(entity)

}
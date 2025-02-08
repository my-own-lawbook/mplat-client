package me.bumiller.mol.data.repository.impl

import me.bumiller.mol.data.mapping.mapSectionModel
import me.bumiller.mol.data.repository.DatabaseSimpleRepository
import me.bumiller.mol.data.repository.LawSectionRepository
import me.bumiller.mol.database.dao.SectionDao
import me.bumiller.mol.database.entities.SectionEntity
import me.bumiller.mol.model.law.LawSection

internal class DatabaseLawSectionRepository(dao: SectionDao) : LawSectionRepository,
    DatabaseSimpleRepository<LawSection, SectionEntity, SectionDao>(dao) {

    override suspend fun createModelFor(entity: SectionEntity) = mapSectionModel(entity)

}
package me.bumiller.civoris.data.repository.impl

import me.bumiller.civoris.data.mapping.mapSectionModel
import me.bumiller.civoris.data.repository.DatabaseSimpleRepository
import me.bumiller.civoris.data.repository.LawSectionRepository
import me.bumiller.civoris.database.dao.SectionDao
import me.bumiller.civoris.database.entities.SectionEntity
import me.bumiller.civoris.model.law.LawSection

internal class DatabaseLawSectionRepository(dao: SectionDao) : LawSectionRepository,
    DatabaseSimpleRepository<LawSection, SectionEntity, SectionDao>(dao) {

    override suspend fun createModelFor(entity: SectionEntity) = mapSectionModel(entity)

}
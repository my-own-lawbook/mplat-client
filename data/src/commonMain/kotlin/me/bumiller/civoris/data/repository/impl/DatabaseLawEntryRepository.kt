package me.bumiller.civoris.data.repository.impl

import me.bumiller.civoris.data.mapping.mapEntryModel
import me.bumiller.civoris.data.repository.DatabaseSimpleRepository
import me.bumiller.civoris.data.repository.LawEntryRepository
import me.bumiller.civoris.database.dao.EntryDao
import me.bumiller.civoris.database.entities.EntryEntity
import me.bumiller.civoris.model.law.LawEntry

internal class DatabaseLawEntryRepository(dao: EntryDao) : LawEntryRepository,
    DatabaseSimpleRepository<LawEntry, EntryEntity, EntryDao>(dao) {

    override suspend fun createModelFor(entity: EntryEntity) = mapEntryModel(entity)

}
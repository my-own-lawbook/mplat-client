package me.bumiller.mol.data.repository.impl

import me.bumiller.mol.data.mapping.entryModel
import me.bumiller.mol.data.repository.DatabaseSimpleRepository
import me.bumiller.mol.data.repository.LawEntryRepository
import me.bumiller.mol.database.dao.EntryDao
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.model.law.LawEntry

internal class DatabaseLawEntryRepository(dao: EntryDao) : LawEntryRepository,
    DatabaseSimpleRepository<LawEntry, EntryEntity, EntryDao>(dao) {

    override suspend fun createModelFor(entity: EntryEntity) = entryModel(entity)

}
package me.bumiller.civoris.data.repository.impl

import me.bumiller.civoris.data.mapping.mapBookModel
import me.bumiller.civoris.data.repository.DatabaseSimpleRepository
import me.bumiller.civoris.data.repository.LawBookRepository
import me.bumiller.civoris.database.dao.BookDao
import me.bumiller.civoris.database.entities.BookEntity
import me.bumiller.civoris.model.law.LawBook

internal class DatabaseLawBookRepository(dao: BookDao) : LawBookRepository,
    DatabaseSimpleRepository<LawBook, BookEntity, BookDao>(dao) {

    override suspend fun createModelFor(entity: BookEntity) = mapBookModel(entity)

}
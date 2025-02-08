package me.bumiller.mol.data.repository.impl

import me.bumiller.mol.data.mapping.bookModel
import me.bumiller.mol.data.repository.DatabaseSimpleRepository
import me.bumiller.mol.data.repository.LawBookRepository
import me.bumiller.mol.database.dao.BookDao
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.model.law.LawBook

internal class DatabaseLawBookRepository(dao: BookDao) : LawBookRepository,
    DatabaseSimpleRepository<LawBook, BookEntity, BookDao>(dao) {

    override suspend fun createModelFor(entity: BookEntity) = bookModel(entity)

}
package me.bumiller.mol.data.repository

import me.bumiller.mol.model.law.LawBook

/**
 * Repository that retrieves law books from a data source.
 */
interface LawBookRepository : SimpleRepository<Long, LawBook>
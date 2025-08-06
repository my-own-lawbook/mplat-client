package me.bumiller.civoris.data.repository

import me.bumiller.civoris.model.law.LawBook

/**
 * Repository that retrieves law books from a data source.
 */
interface LawBookRepository : SimpleRepository<Long, LawBook>
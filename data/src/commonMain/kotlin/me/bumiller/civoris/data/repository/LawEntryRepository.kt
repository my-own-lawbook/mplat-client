package me.bumiller.civoris.data.repository

import me.bumiller.civoris.model.law.LawEntry

/**
 * Repository that retrieves law entries from a data source.
 */
interface LawEntryRepository : SimpleRepository<Long, LawEntry>
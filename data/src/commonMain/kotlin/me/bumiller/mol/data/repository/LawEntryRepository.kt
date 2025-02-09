package me.bumiller.mol.data.repository

import me.bumiller.mol.model.law.LawEntry

/**
 * Repository that retrieves law entries from a data source.
 */
interface LawEntryRepository : SimpleRepository<Long, LawEntry>
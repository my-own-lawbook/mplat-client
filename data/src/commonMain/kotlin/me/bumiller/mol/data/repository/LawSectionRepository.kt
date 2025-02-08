package me.bumiller.mol.data.repository

import me.bumiller.mol.model.law.LawSection

/**
 * Repository that retrieves law sections from a data source.
 */
interface LawSectionRepository : SimpleRepository<Long, LawSection>
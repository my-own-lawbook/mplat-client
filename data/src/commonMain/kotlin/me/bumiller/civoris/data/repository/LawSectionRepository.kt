package me.bumiller.civoris.data.repository

import me.bumiller.civoris.model.law.LawSection

/**
 * Repository that retrieves law sections from a data source.
 */
interface LawSectionRepository : SimpleRepository<Long, LawSection>
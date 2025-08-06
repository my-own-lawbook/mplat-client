package me.bumiller.civoris.data.repository

import me.bumiller.civoris.model.law.ForeignUser

/**
 * Repository that retrieves users from a data source.
 */
interface ForeignUserRepository : SimpleRepository<Long, ForeignUser>
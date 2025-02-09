package me.bumiller.mol.data.repository

import me.bumiller.mol.model.law.ForeignUser

/**
 * Repository that retrieves users from a data source.
 */
interface ForeignUserRepository : SimpleRepository<Long, ForeignUser>
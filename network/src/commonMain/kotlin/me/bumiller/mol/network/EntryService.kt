package me.bumiller.mol.network

import me.bumiller.mol.network.base.SimpleResourceService
import me.bumiller.mol.network.response.LawEntryResponse

/**
 * Service that accesses the law-entry resource on the rest api.
 */
interface EntryService : SimpleResourceService<LawEntryResponse>
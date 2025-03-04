package me.bumiller.mol.network

import me.bumiller.mol.network.base.ResourceParentService
import me.bumiller.mol.network.response.LawEntryResponse

/**
 * Service that accesses the law-entry resource on the rest api.
 */
interface EntryService : ResourceParentService<LawEntryResponse>
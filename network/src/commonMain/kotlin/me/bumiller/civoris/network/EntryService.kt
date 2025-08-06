package me.bumiller.civoris.network

import me.bumiller.civoris.network.base.ResourceParentService
import me.bumiller.civoris.network.response.LawEntryResponse

/**
 * Service that accesses the law-entry resource on the rest api.
 */
interface EntryService : ResourceParentService<LawEntryResponse>
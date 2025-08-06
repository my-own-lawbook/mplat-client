package me.bumiller.civoris.network

import me.bumiller.civoris.network.base.ResourceParentService
import me.bumiller.civoris.network.response.LawSectionResponse

/**
 * Service that accesses the law-sections resource on the rest api.
 */
interface SectionService : ResourceParentService<LawSectionResponse>
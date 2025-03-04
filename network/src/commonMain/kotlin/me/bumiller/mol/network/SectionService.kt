package me.bumiller.mol.network

import me.bumiller.mol.network.base.ResourceParentService
import me.bumiller.mol.network.response.LawSectionResponse

/**
 * Service that accesses the law-sections resource on the rest api.
 */
interface SectionService : ResourceParentService<LawSectionResponse>
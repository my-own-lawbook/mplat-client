package me.bumiller.mol.network

import me.bumiller.mol.network.base.SimpleResourceService
import me.bumiller.mol.network.response.LawSectionResponse

/**
 * Service that accesses the law-sections resource on the rest api.
 */
interface SectionService : SimpleResourceService<LawSectionResponse>
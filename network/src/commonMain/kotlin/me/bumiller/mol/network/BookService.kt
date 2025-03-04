package me.bumiller.mol.network

import me.bumiller.mol.network.base.SimpleResourceService
import me.bumiller.mol.network.response.LawBookResponse

/**
 * Service that accesses the law-books resource on the rest api.
 */
interface BookService : SimpleResourceService<LawBookResponse>
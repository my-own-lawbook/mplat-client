package me.bumiller.civoris.network

import me.bumiller.civoris.network.base.SimpleResourceService
import me.bumiller.civoris.network.response.LawBookResponse

/**
 * Service that accesses the law-books resource on the rest api.
 */
interface BookService : SimpleResourceService<LawBookResponse>
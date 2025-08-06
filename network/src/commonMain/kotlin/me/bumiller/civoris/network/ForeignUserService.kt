package me.bumiller.civoris.network

import me.bumiller.civoris.network.base.SimpleResourceService
import me.bumiller.civoris.network.response.ForeignUserResponse

/**
 * Service that accesses the foreign user resource on the rest api.
 */
interface ForeignUserService : SimpleResourceService<ForeignUserResponse>
package me.bumiller.mol.network

import me.bumiller.mol.network.base.SimpleResourceService
import me.bumiller.mol.network.response.ForeignUserResponse

/**
 * Service that accesses the foreign user resource on the rest api.
 */
interface ForeignUserService : SimpleResourceService<ForeignUserResponse>
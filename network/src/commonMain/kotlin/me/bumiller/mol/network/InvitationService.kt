package me.bumiller.mol.network

import me.bumiller.mol.network.base.SimpleResourceService
import me.bumiller.mol.network.response.BookInvitationResponse

/**
 * Service that accesses the invitation resource on the rest api.
 */
interface InvitationService : SimpleResourceService<BookInvitationResponse>
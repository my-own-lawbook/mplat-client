package me.bumiller.civoris.network

import me.bumiller.civoris.network.base.SimpleResourceService
import me.bumiller.civoris.network.response.BookInvitationResponse

/**
 * Service that accesses the invitation resource on the rest api.
 */
interface InvitationService : SimpleResourceService<BookInvitationResponse>
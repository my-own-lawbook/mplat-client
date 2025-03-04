package me.bumiller.mol.sync.model

import me.bumiller.mol.network.response.BookInvitationResponse
import me.bumiller.mol.network.response.ForeignUserResponse
import me.bumiller.mol.network.response.LawBookResponse
import me.bumiller.mol.network.response.LawEntryResponse
import me.bumiller.mol.network.response.LawSectionResponse

/**
 * Models all the data to be synced.
 */
internal data class NetworkData(

    /**
     * The books pulled from the server.
     */
    val books: List<LawBookResponse>,

    /**
     * The entries pulled from the server.
     */
    val entries: List<LawEntryResponse>,

    /**
     * The sections pulled from the server.
     */
    val sections: List<LawSectionResponse>,

    /**
     * The users pulled from the server.
     */
    val users: List<ForeignUserResponse>,

    /**
     * The invitations pulled from the server.
     */
    val invitations: List<BookInvitationResponse>

)

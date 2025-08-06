package me.bumiller.civoris.model.aggregate

import me.bumiller.civoris.model.Identifiable
import me.bumiller.civoris.model.law.ForeignUser
import me.bumiller.civoris.model.law.LawBook
import me.bumiller.civoris.model.law.LawBookInvitation

/**
 * Contains a book invitation with more loaded information.
 */
data class LawBookInvitationAggregate(

    /**
     * The actual invitation.
     */
    val invitation: LawBookInvitation,

    /**
     * The author of the invitation.
     */
    val author: ForeignUser,

    /**
     * The target of the invitation.
     */
    val target: LawBook,

    override val id: Long = invitation.id
) : Identifiable<Long>

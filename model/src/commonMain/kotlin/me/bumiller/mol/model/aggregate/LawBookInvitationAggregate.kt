package me.bumiller.mol.model.aggregate

import me.bumiller.mol.model.Identifiable
import me.bumiller.mol.model.law.ForeignUser
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.law.LawBookInvitation

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

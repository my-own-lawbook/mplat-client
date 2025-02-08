package me.bumiller.mol.model.law

import kotlinx.datetime.Instant
import me.bumiller.mol.model.Identifiable

/**
 * Class that models an invitation into a law book.
 */
data class LawBookInvitation(

    override val id: Long,

    /**
     * The id of the author [ForeignUser] that sent the invitation.
     */
    val authorId: Long,

    /**
     * The id of tha recipient [ForeignUser] that received the invitation.
     */
    val recipientId: Long,

    /**
     * The id of the [LawBook] that the invitation is targeted towards.
     */
    val targetId: Long,

    /**
     * The role that comes with accepting the invitation.
     */
    val role: MemberRole,

    /**
     * The timestamp at which the invitation was sent.
     */
    val sentTimestamp: Instant,

    /**
     * The timestamp at which the invitation was used (i.e. accepted, denied or revoked), or null if it is still open.
     */
    val usedTimestamp: Instant?,

    /**
     * The timestamp at which the invitation has/will expired(d), or null if the invitation will never expire.
     */
    val expiredTimestamp: Instant?,

    /**
     * The current status of the invitation.
     */
    val status: InvitationStatus,

    /**
     * The message, or null if none was included.
     */
    val message: String?


) : Identifiable<Long>
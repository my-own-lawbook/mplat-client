package me.bumiller.mol.network.response

import kotlinx.serialization.Serializable

/**
 * Response class for the law-invitation resource.
 */
@Serializable
data class BookInvitationResponse(

    override val id: Long,

    /**
     * The id of the author.
     */
    val authorId: Long,

    /**
     * The id of the target book.
     */
    val targetBookId: Long,

    /**
     * The id of the recipient.
     */
    val recipientId: Long,

    /**
     * The role that will be applied to the recipient.
     */
    val role: String,

    /**
     * The timestamp of the sending.
     */
    val sentAt: String,

    /**
     * The timestamp of the using.
     */
    val usedAt: String?,

    /**
     * The current status
     */
    val status: String,

    /**
     * The timestamp of expiry.
     */
    val expiredAt: String?,

    /**
     * The message that was sent along.
     */
    val message: String?

) : RestResponse

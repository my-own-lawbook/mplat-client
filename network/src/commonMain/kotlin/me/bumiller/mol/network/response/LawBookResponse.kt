package me.bumiller.mol.network.response

import kotlinx.serialization.Serializable

/**
 * Response class for the law-book resource.
 */
@Serializable
data class LawBookResponse(

    override val id: Long,

    /**
     * The key of the book.
     */
    val key: String,

    /**
     * The name of the book.
     */
    val name: String,

    /**
     * The description of the book.
     */
    val description: String,

    /**
     * Whether the user is a member of the book, or only has temporary access to it due to a pending invitation.
     */
    val isMemberOf: Boolean

) : RestResponse
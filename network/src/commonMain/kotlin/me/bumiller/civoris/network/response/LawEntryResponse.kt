package me.bumiller.civoris.network.response

import kotlinx.serialization.Serializable

/**
 * Response class for the law-entry resource.
 */
@Serializable
data class LawEntryResponse(

    override val id: Long,

    /**
     * The key of the entry.
     */
    val key: String,

    /**
     * The name of the entry.
     */
    val name: String

) : RestResponse

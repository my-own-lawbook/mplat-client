package me.bumiller.civoris.network.response

import kotlinx.serialization.Serializable

/**
 * Response class for the law-section resource.
 */
@Serializable
data class LawSectionResponse(

    override val id: Long,

    /**
     * The index of the section.
     */
    val index: String,

    /**
     * The name of the section.
     */
    val name: String,

    /**
     * The content of the section.
     */
    val content: String

) : RestResponse

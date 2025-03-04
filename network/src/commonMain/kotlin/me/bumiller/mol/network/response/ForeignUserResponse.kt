package me.bumiller.mol.network.response

import kotlinx.serialization.Serializable

/**
 * Response class for the foreign user resource.
 */
@Serializable
data class ForeignUserResponse(

    override val id: Long,

    /**
     * The username of the user.
     */
    val username: String,

    /**
     * The profile response of the user.
     */
    val profile: UserProfileResponse

) : RestResponse

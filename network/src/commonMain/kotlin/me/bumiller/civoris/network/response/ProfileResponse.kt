package me.bumiller.civoris.network.response

import kotlinx.serialization.Serializable

/**
 * Response class for a profile.
 */
@Serializable
data class ProfileResponse(

    /**
     * The birthday of the user.
     */
    val birthday: String,

    /**
     * The gender of the user.
     */
    val gender: String,

    /**
     * The first name of the user.
     */
    val firstName: String,

    /**
     * The last name of te user.
     */
    val lastName: String

)
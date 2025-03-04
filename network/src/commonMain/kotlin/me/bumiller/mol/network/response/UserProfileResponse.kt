package me.bumiller.mol.network.response

import kotlinx.serialization.Serializable

/**
 * Response class that contains information about a users profile
 */
@Serializable
data class UserProfileResponse(

    val birthday: String,

    val gender: String,

    val firstName: String,

    val lastName: String

)
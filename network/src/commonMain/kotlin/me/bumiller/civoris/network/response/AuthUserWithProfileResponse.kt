package me.bumiller.civoris.network.response

import kotlinx.serialization.Serializable

/**
 * Response class that contains a user with its profile
 */
@Serializable
data class AuthUserWithProfileResponse(

    val id: Long,

    val email: String,

    val username: String,

    val isEmailVerified: Boolean,

    val profile: UserProfileResponse

)
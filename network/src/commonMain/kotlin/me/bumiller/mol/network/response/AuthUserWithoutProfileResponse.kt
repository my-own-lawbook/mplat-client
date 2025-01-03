package me.bumiller.mol.network.response

import kotlinx.serialization.Serializable

/**
 * Response for endpoints that return data about a user without the users profile
 */
@Serializable
data class AuthUserWithoutProfileResponse(

    val id: Long,

    val email: String,

    val username: String,

    val isEmailVerified: Boolean

)

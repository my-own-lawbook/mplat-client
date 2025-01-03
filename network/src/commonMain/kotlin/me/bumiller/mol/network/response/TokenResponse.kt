package me.bumiller.mol.network.response

import kotlinx.serialization.Serializable

/**
 * Response for endpoints that return an access (jwt) token and a refresh token
 */
@Serializable
data class TokenResponse(

    /**
     * The access token
     */
    val accessToken: String,

    /**
     * The refresh token
     */
    val refreshToken: String

)
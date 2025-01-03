package me.bumiller.mol.network

import kotlinx.serialization.Serializable
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.response.TokenResponse

/**
 * Service to access the http auth api of the backend.
 */
interface AuthApi {

    /**
     * Request body for a request to POST /auth/login/.
     */
    @Serializable
    data class LoginCredentialsRequest(val email: String, val password: String)

    /**
     * Request to POST /auth/login/.
     *
     * @param body The body
     */
    suspend fun loginCredentials(body: LoginCredentialsRequest): NetworkResponse<TokenResponse>

    /**
     * Request body for a request to POST /auth/login/refresh/
     */
    @Serializable
    data class LoginRefreshRequest(val token: String)

    /**
     * Request to POST /auth/login/refresh/.
     *
     * @param body The body
     */
    suspend fun loginCredentials(body: LoginRefreshRequest): NetworkResponse<TokenResponse>

}
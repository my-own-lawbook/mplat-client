package me.bumiller.mol.auth

import me.bumiller.mol.model.user.AuthUser
import me.bumiller.mol.model.user.AuthUserWithProfile
import me.bumiller.mol.model.user.Profile
import me.bumiller.mol.network.model.NetworkResponse

/**
 * Service to handle authentication to the backend.
 */
interface AuthService {

    /**
     * Attempts to login with credentials and saves the updated tokens to the local storage.
     *
     * @param email The email
     * @param password The password
     * @return The [AuthResult]
     */
    suspend fun login(email: String, password: String): AuthResult<AuthUser, LoginError>

    /**
     * Attempts to login with the stored refresh token and saves the updated tokens to the local storage.
     */
    suspend fun login(): AuthResult<AuthUser, LoginError>

    /**
     * Attempts to sign up.
     *
     * @param email The users email
     * @param username The users username
     * @param password The users password
     */
    suspend fun createUser(
        email: String,
        username: String,
        password: String
    ): AuthResult<AuthUser, SignupError>

    /**
     * Submits a given email token.
     *
     * @param token The email token
     */
    suspend fun submitEmailToken(token: String): AuthResult<Unit, SubmitEmailTokenError>

    /**
     * Requests an email token to the email of the authentication.
     */
    suspend fun requestEmailToken(): AuthResult<Unit, RequestEmailTokenError>

    /**
     * Gets the profile of the authenticated user.
     */
    suspend fun getProfile(): AuthResult<Profile, GetProfileError>

    /**
     * Updates the profile of the authenticated user.
     *
     * @param profile The profile to upload.
     */
    suspend fun setProfile(profile: Profile): AuthResult<AuthUserWithProfile, Unit>

    /**
     * Executes a network call, and retries it when the error response may be caused by unauthenticated.
     *
     * @param call The call
     * @return The first of the two calls which results in a successful response, or the second error response
     */
    suspend fun <Data> safeAuthCall(call: suspend () -> NetworkResponse<Data>): NetworkResponse<Data>

}
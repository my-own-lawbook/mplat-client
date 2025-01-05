package me.bumiller.mol.auth.impl

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import me.bumiller.mol.auth.AuthResult
import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.auth.LoginError
import me.bumiller.mol.auth.RequestEmailTokenError
import me.bumiller.mol.auth.SignupError
import me.bumiller.mol.auth.SubmitEmailTokenError
import me.bumiller.mol.network.model.ErrorInfo
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.response.AuthUserWithoutProfileResponse
import me.bumiller.mol.network.response.TokenResponse
import me.bumiller.mol.network.wrapper.performPatch
import me.bumiller.mol.network.wrapper.performPost
import me.bumiller.mol.settings.UserSettingsSource

internal class KtorAuthService(
    private val client: HttpClient,
    private val settingsSource: UserSettingsSource
) : AuthService {

    private inline fun <reified Body, ErrorType> NetworkResponse<Body>.asAuthResult(
        getSpecificError: (Int, ErrorInfo) -> ErrorType?
    ): AuthResult<ErrorType> {
        return when (this) {
            is NetworkResponse.HttpError -> getSpecificError(
                code,
                info
            )?.let { AuthResult.Error(it) }
                ?: AuthResult.UnknownError()

            is NetworkResponse.NetworkError -> AuthResult.NetworkError()
            is NetworkResponse.Success -> AuthResult.Success()
        }
    }

    @Serializable
    data class CredentialsBody(val email: String, val password: String)

    override suspend fun login(email: String, password: String): AuthResult<LoginError> {
        val body = CredentialsBody(email, password)
        val response = client.performPost<TokenResponse>("auth/login/", body)

        if (response is NetworkResponse.Success) {
            val settings = settingsSource.settings.first()
            settingsSource.update(
                settings.copy(
                    accessToken = response.data.accessToken,
                    refreshToken = response.data.refreshToken
                )
            )
        }

        return response.asAuthResult { code, _ ->
            when (code) {
                401 -> LoginError.BadCredentials
                else -> null
            }
        }
    }

    @Serializable
    data class TokenBody(val token: String)

    override suspend fun login(): AuthResult<LoginError> {
        val token = settingsSource.settings.value.refreshToken ?: ""
        val body = TokenBody(token)
        val response = client.performPost<TokenResponse>("auth/login/refresh/", body)

        if (response is NetworkResponse.Success) {
            val settings = settingsSource.settings.first()
            settingsSource.update(
                settings.copy(
                    accessToken = response.data.accessToken,
                    refreshToken = response.data.refreshToken
                )
            )
        }

        return response.asAuthResult { code, info ->
            when (code) {
                401 -> LoginError.BadToken
                else -> when (info) {
                    is ErrorInfo.BadFormatInfo -> LoginError.BadToken
                    else -> null
                }
            }
        }
    }

    @Serializable
    data class CreateUserBody(val email: String, val username: String, val password: String)

    override suspend fun createUser(
        email: String,
        username: String,
        password: String
    ): AuthResult<SignupError> {
        val body = CreateUserBody(email, username, password)

        val response = client.performPost<AuthUserWithoutProfileResponse>("auth/signup/", body)

        return response.asAuthResult { _, info ->
            if (info is ErrorInfo.ConflictUniqueInfo) {
                when (info.field) {
                    "email" -> SignupError.EmailNotUnique
                    "username" -> SignupError.UsernameNotUnique
                    else -> null
                }
            } else null
        }
    }

    override suspend fun submitEmailToken(token: String): AuthResult<SubmitEmailTokenError> {
        val body = TokenBody(token)

        val response = client.performPatch<Unit>("auth/signup/email-verify", body)

        return response.asAuthResult { code, _ ->
            when (code) {
                404 -> SubmitEmailTokenError.InvalidToken
                else -> null
            }
        }
    }

    override suspend fun requestEmailToken(): AuthResult<RequestEmailTokenError> {
        val response = safeAuthCall {
            client.performPost<Unit>("auth/signup/email-verify/", Unit)
        }

        return response.asAuthResult { code, _ ->
            when (code) {
                404 -> RequestEmailTokenError.NotAuthenticated
                else -> null
            }
        }
    }

    private val authorizationIndicatingCodes = listOf(401, 403, 403)

    override suspend fun <Data> safeAuthCall(call: suspend () -> NetworkResponse<Data>): NetworkResponse<Data> {
        val firstResponse = call()

        val shouldRetry = firstResponse is NetworkResponse.HttpError &&
                firstResponse.code in authorizationIndicatingCodes

        if (shouldRetry) {
            login()
            return call()
        }

        return firstResponse
    }


}
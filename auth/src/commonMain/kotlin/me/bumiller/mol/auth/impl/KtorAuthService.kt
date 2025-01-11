package me.bumiller.mol.auth.impl

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import me.bumiller.mol.auth.AuthResult
import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.auth.GetProfileError
import me.bumiller.mol.auth.LoginError
import me.bumiller.mol.auth.RequestEmailTokenError
import me.bumiller.mol.auth.SignupError
import me.bumiller.mol.auth.SubmitEmailTokenError
import me.bumiller.mol.auth.mapping.toModel
import me.bumiller.mol.auth.mapping.toRequestString
import me.bumiller.mol.model.user.AuthUser
import me.bumiller.mol.model.user.AuthUserWithProfile
import me.bumiller.mol.model.user.Profile
import me.bumiller.mol.network.model.ErrorInfo
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.response.AuthUserWithProfileResponse
import me.bumiller.mol.network.response.AuthUserWithoutProfileResponse
import me.bumiller.mol.network.response.TokenResponse
import me.bumiller.mol.network.response.UserProfileResponse
import me.bumiller.mol.network.wrapper.performGet
import me.bumiller.mol.network.wrapper.performPatch
import me.bumiller.mol.network.wrapper.performPost
import me.bumiller.mol.settings.UserSettingsSource

internal class KtorAuthService(
    private val client: HttpClient,
    private val settingsSource: UserSettingsSource
) : AuthService {

    private inline fun <reified ErrorBody, Body, ErrorType> NetworkResponse<ErrorBody>.asAuthResult(
        data: NetworkResponse<Body>,
        getSpecificError: (Int, ErrorInfo) -> ErrorType?
    ): AuthResult<Body, ErrorType> {
        return when (this) {
            is NetworkResponse.HttpError -> getSpecificError(
                code,
                info
            )?.let { AuthResult.Error(it) }
                ?: AuthResult.UnknownError(code = code, errorInfo = info)

            is NetworkResponse.NetworkError -> AuthResult.NetworkError()
            is NetworkResponse.Success -> when (data) {
                is NetworkResponse.Success -> AuthResult.Success(data.data)
                is NetworkResponse.HttpError -> AuthResult.UnknownError(
                    code = data.code,
                    errorInfo = data.info
                )

                is NetworkResponse.NetworkError -> AuthResult.NetworkError()
            }
        }
    }

    @Serializable
    data class CredentialsBody(val email: String, val password: String)

    override suspend fun login(email: String, password: String): AuthResult<AuthUser, LoginError> {
        val body = CredentialsBody(email, password)
        val loginResponse = client.performPost<TokenResponse>("auth/login/", body)

        if (loginResponse is NetworkResponse.Success) {
            val settings = settingsSource.settings.first()
            settingsSource.update(
                settings.copy(
                    accessToken = loginResponse.data.accessToken,
                    refreshToken = loginResponse.data.refreshToken
                )
            )
        }

        val userResponse = client.performGet<AuthUserWithoutProfileResponse>("user/")
            .map(AuthUserWithoutProfileResponse::toModel)

        return loginResponse.asAuthResult(userResponse) { code, _ ->
            when (code) {
                401 -> LoginError.BadCredentials
                else -> null
            }
        }
    }

    @Serializable
    data class TokenBody(val token: String)

    override suspend fun login(): AuthResult<AuthUser, LoginError> {
        val token = settingsSource.settings.value.refreshToken ?: ""
        val body = TokenBody(token)
        val loginResponse = client.performPost<TokenResponse>("auth/login/refresh/", body)

        if (loginResponse is NetworkResponse.Success) {
            val settings = settingsSource.settings.first()
            settingsSource.update(
                settings.copy(
                    accessToken = loginResponse.data.accessToken,
                    refreshToken = loginResponse.data.refreshToken
                )
            )
        }

        val userResponse = client.performGet<AuthUserWithoutProfileResponse>("user/")
            .map(AuthUserWithoutProfileResponse::toModel)

        return loginResponse.asAuthResult(userResponse) { code, info ->
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
    ): AuthResult<AuthUser, SignupError> {
        val body = CreateUserBody(email, username, password)

        val signupResponse =
            client.performPost<AuthUserWithoutProfileResponse>("auth/signup/", body)
                .map(AuthUserWithoutProfileResponse::toModel)
        login(email, password)

        return signupResponse.asAuthResult(signupResponse) { _, info ->
            if (info is ErrorInfo.ConflictUniqueInfo) {
                when (info.field) {
                    "email" -> SignupError.EmailNotUnique
                    "username" -> SignupError.UsernameNotUnique
                    else -> null
                }
            } else null
        }
    }

    override suspend fun submitEmailToken(token: String): AuthResult<Unit, SubmitEmailTokenError> {
        val body = TokenBody(token)

        val response = client.performPatch<Unit>("auth/signup/email-verify/", body)

        return response.asAuthResult(NetworkResponse.Success(Unit)) { code, _ ->
            when (code) {
                404 -> SubmitEmailTokenError.InvalidToken
                else -> null
            }
        }
    }

    override suspend fun requestEmailToken(): AuthResult<Unit, RequestEmailTokenError> {
        val response = safeAuthCall {
            client.performPost<Unit>("auth/signup/email-verify/", Unit)
        }

        return response.asAuthResult(NetworkResponse.Success(Unit)) { code, _ ->
            when (code) {
                404 -> RequestEmailTokenError.NotAuthenticated
                else -> null
            }
        }
    }

    override suspend fun getProfile(): AuthResult<Profile, GetProfileError> {
        val response = safeAuthCall {
            client.performGet<UserProfileResponse>("user/profile/")
                .map(UserProfileResponse::toModel)
        }

        return response.asAuthResult(response) { code, _ ->
            when (code) {
                404 -> GetProfileError.NotSet
                401 -> GetProfileError.NotAuthenticated
                else -> null
            }
        }
    }

    @Serializable
    data class SetProfileBody(
        val firstName: String,
        val lastName: String,
        val birthday: LocalDate,
        val gender: String
    )

    override suspend fun setProfile(profile: Profile): AuthResult<AuthUserWithProfile, Unit> {
        val body = SetProfileBody(
            profile.firstName,
            profile.lastName,
            profile.birthday,
            profile.gender.toRequestString()
        )

        val response = safeAuthCall {
            client.performPost<AuthUserWithProfileResponse>("user/profile/", body)
                .map(AuthUserWithProfileResponse::toModel)
        }

        return response.asAuthResult(response) { _, _ -> }
    }

    private val authorizationIndicatingCodes = listOf(401, 403, 404)

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
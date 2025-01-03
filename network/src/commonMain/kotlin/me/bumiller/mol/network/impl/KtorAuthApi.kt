package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import me.bumiller.mol.network.AuthApi
import me.bumiller.mol.network.response.AuthUserWithoutProfileResponse
import me.bumiller.mol.network.response.TokenResponse
import me.bumiller.mol.network.wrapper.performPatch
import me.bumiller.mol.network.wrapper.performPost

internal class KtorAuthApi(
    private val client: HttpClient
) : AuthApi {

    override suspend fun loginCredentials(body: AuthApi.LoginCredentialsRequest) =
        client.performPost<TokenResponse>("/auth/login/", body)

    override suspend fun loginCredentials(body: AuthApi.LoginRefreshRequest) =
        client.performPost<TokenResponse>("/auth/login/refresh/", body)

    override suspend fun createUser(body: AuthApi.CreateUserRequest) =
        client.performPost<AuthUserWithoutProfileResponse>("/auth/signup/", body)

    override suspend fun submitEmailToken(body: AuthApi.SubmitEmailTokenRequest) =
        client.performPatch<Unit>("/auth/signup/email-verify/", body)

}
package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import me.bumiller.mol.network.AuthApi
import me.bumiller.mol.network.response.TokenResponse
import me.bumiller.mol.network.wrapper.performPost

internal class KtorAuthApi(
    private val client: HttpClient
) : AuthApi {

    override suspend fun loginCredentials(body: AuthApi.LoginCredentialsRequest) =
        client.performPost<TokenResponse>("/auth/login/", body)

    override suspend fun loginCredentials(body: AuthApi.LoginRefreshRequest) =
        client.performPost<TokenResponse>("/auth/login/refresh/", body)

}
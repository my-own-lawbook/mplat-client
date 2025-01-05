package me.bumiller.mol.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import me.bumiller.mol.network.ServerStatusChecker
import me.bumiller.mol.network.impl.KtorServerStatusChecker
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.plugin.DynamicUrl
import me.bumiller.mol.network.response.TokenResponse
import me.bumiller.mol.network.wrapper.performPost
import me.bumiller.mol.settings.UserSettingsSource
import org.koin.core.scope.Scope
import org.koin.dsl.module

/**
 * Koin module for the network module.
 */
val networkModule = module {
    single<ServerStatusChecker> { KtorServerStatusChecker() }

    single { instantiateKtorClient() }
}

private fun Scope.instantiateKtorClient(): HttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = Logger.SIMPLE
    }

    install(DynamicUrl(get()))

    install(Auth) {
        bearer {
            loadTokens {
                val settings = get<UserSettingsSource>().settings.first()

                val access = settings.accessToken
                val refresh = settings.refreshToken

                if (access != null && refresh != null) {
                    BearerTokens(access, refresh)
                } else null
            }

            refreshTokens {
                val source = get<UserSettingsSource>()

                val response = client.performPost<TokenResponse>("auth/login/refresh/", object {
                    val token = oldTokens?.refreshToken ?: ""
                })

                when (response) {
                    is NetworkResponse.Success -> {
                        val settings = source.settings.first()
                        source.update(
                            settings.copy(
                                accessToken = response.data.accessToken,
                                refreshToken = response.data.refreshToken
                            )
                        )

                        BearerTokens(
                            response.data.accessToken, response.data.refreshToken
                        )
                    }

                    else -> null
                }
            }
        }
    }
}
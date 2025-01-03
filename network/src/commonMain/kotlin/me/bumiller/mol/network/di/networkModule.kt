package me.bumiller.mol.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import me.bumiller.mol.network.AuthApi
import me.bumiller.mol.network.ServerStatusChecker
import me.bumiller.mol.network.impl.KtorAuthApi
import me.bumiller.mol.network.impl.KtorServerStatusChecker
import me.bumiller.mol.network.plugin.DynamicUrl
import org.koin.core.scope.Scope
import org.koin.dsl.module

/**
 * Koin module for the network module.
 */
val networkModule = module {
    single<ServerStatusChecker> { KtorServerStatusChecker() }

    single { instantiateKtorClient() }

    single<AuthApi> { KtorAuthApi(get()) }
}

private fun Scope.instantiateKtorClient(): HttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }

    install(Logging)

    install(DynamicUrl(get()))
}
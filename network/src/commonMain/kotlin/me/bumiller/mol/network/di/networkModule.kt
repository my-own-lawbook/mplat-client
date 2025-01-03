package me.bumiller.mol.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import me.bumiller.mol.network.AuthApi
import me.bumiller.mol.network.ServerStatusChecker
import me.bumiller.mol.network.impl.KtorAuthApi
import me.bumiller.mol.network.impl.KtorServerStatusChecker
import org.koin.dsl.module

/**
 * Koin module for the network module.
 */
val networkModule = module {
    single<ServerStatusChecker> { KtorServerStatusChecker() }

    single { instantiateKtorClient() }

    single<AuthApi> { KtorAuthApi(get()) }
}

private fun instantiateKtorClient(): HttpClient = HttpClient(CIO)
package me.bumiller.civoris.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import me.bumiller.civoris.network.BookService
import me.bumiller.civoris.network.EntryService
import me.bumiller.civoris.network.ForeignUserService
import me.bumiller.civoris.network.InvitationService
import me.bumiller.civoris.network.SectionService
import me.bumiller.civoris.network.ServerStatusChecker
import me.bumiller.civoris.network.impl.KtorBookService
import me.bumiller.civoris.network.impl.KtorEntryService
import me.bumiller.civoris.network.impl.KtorForeignUserService
import me.bumiller.civoris.network.impl.KtorInvitationService
import me.bumiller.civoris.network.impl.KtorSectionService
import me.bumiller.civoris.network.impl.KtorServerStatusChecker
import me.bumiller.civoris.network.plugin.DynamicUrl
import me.bumiller.civoris.network.plugin.CivorisAuthPlugin
import org.koin.core.scope.Scope
import org.koin.dsl.module

/**
 * Koin module for the network module.
 */
val networkModule = module {
    single<ServerStatusChecker> { KtorServerStatusChecker(get()) }

    single<HttpClientEngineFactory<*>> { instantiateClientEngineFactory() }

    single { instantiateKtorClient() }

    single<BookService> { KtorBookService(get()) }
    single<EntryService> { KtorEntryService(get()) }
    single<SectionService> { KtorSectionService(get()) }
    single<ForeignUserService> { KtorForeignUserService(get()) }
    single<InvitationService> { KtorInvitationService(get()) }
}

/**
 * Platform-dependant method to create an [HttpClientEngineFactory].
 */
internal expect fun Scope.instantiateClientEngineFactory(): HttpClientEngineFactory<*>

private fun Scope.instantiateKtorClient(): HttpClient =
    HttpClient(get<HttpClientEngineFactory<*>>()) {
    install(ContentNegotiation) {
        json()
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = Logger.SIMPLE
    }

    install(DynamicUrl(get()))
    install(CivorisAuthPlugin(get()))
}
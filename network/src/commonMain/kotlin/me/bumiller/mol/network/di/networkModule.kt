package me.bumiller.mol.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import me.bumiller.mol.network.BookService
import me.bumiller.mol.network.EntryService
import me.bumiller.mol.network.ForeignUserService
import me.bumiller.mol.network.InvitationService
import me.bumiller.mol.network.SectionService
import me.bumiller.mol.network.ServerStatusChecker
import me.bumiller.mol.network.impl.KtorBookService
import me.bumiller.mol.network.impl.KtorEntryService
import me.bumiller.mol.network.impl.KtorForeignUserService
import me.bumiller.mol.network.impl.KtorInvitationService
import me.bumiller.mol.network.impl.KtorSectionService
import me.bumiller.mol.network.impl.KtorServerStatusChecker
import me.bumiller.mol.network.plugin.DynamicUrl
import me.bumiller.mol.network.plugin.MolAuth
import org.koin.core.scope.Scope
import org.koin.dsl.module

/**
 * Koin module for the network module.
 */
val networkModule = module {
    single<ServerStatusChecker> { KtorServerStatusChecker() }

    single { instantiateKtorClient() }

    single<BookService> { KtorBookService(get()) }
    single<EntryService> { KtorEntryService(get()) }
    single<SectionService> { KtorSectionService(get()) }
    single<ForeignUserService> { KtorForeignUserService(get()) }
    single<InvitationService> { KtorInvitationService(get()) }
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
    install(MolAuth(get()))
}
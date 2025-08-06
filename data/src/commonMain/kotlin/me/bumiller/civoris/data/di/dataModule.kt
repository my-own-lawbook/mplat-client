package me.bumiller.civoris.data.di

import me.bumiller.civoris.data.ConnectionService
import me.bumiller.civoris.data.impl.ConnectionServiceImpl
import me.bumiller.civoris.data.repository.ForeignUserRepository
import me.bumiller.civoris.data.repository.LawBookInvitationRepository
import me.bumiller.civoris.data.repository.LawBookRepository
import me.bumiller.civoris.data.repository.LawEntryRepository
import me.bumiller.civoris.data.repository.LawSectionRepository
import me.bumiller.civoris.data.repository.impl.DatabaseBookInvitationRepository
import me.bumiller.civoris.data.repository.impl.DatabaseForeignUserRepository
import me.bumiller.civoris.data.repository.impl.DatabaseLawBookRepository
import me.bumiller.civoris.data.repository.impl.DatabaseLawEntryRepository
import me.bumiller.civoris.data.repository.impl.DatabaseLawSectionRepository
import org.koin.dsl.module

/**
 * The koin-module for the data module
 */
val dataModule = module {
    single<ConnectionService> { ConnectionServiceImpl(get(), get()) }

    single<ForeignUserRepository> { DatabaseForeignUserRepository(get()) }
    single<LawBookInvitationRepository> { DatabaseBookInvitationRepository(get()) }
    single<LawBookRepository> { DatabaseLawBookRepository(get()) }
    single<LawEntryRepository> { DatabaseLawEntryRepository(get()) }
    single<LawSectionRepository> { DatabaseLawSectionRepository(get()) }
}
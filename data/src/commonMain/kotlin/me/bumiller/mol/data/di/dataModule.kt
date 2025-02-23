package me.bumiller.mol.data.di

import me.bumiller.mol.data.ConnectionService
import me.bumiller.mol.data.impl.ConnectionServiceImpl
import me.bumiller.mol.data.repository.ForeignUserRepository
import me.bumiller.mol.data.repository.LawBookInvitationRepository
import me.bumiller.mol.data.repository.LawBookRepository
import me.bumiller.mol.data.repository.LawEntryRepository
import me.bumiller.mol.data.repository.LawSectionRepository
import me.bumiller.mol.data.repository.impl.DatabaseBookInvitationRepository
import me.bumiller.mol.data.repository.impl.DatabaseForeignUserRepository
import me.bumiller.mol.data.repository.impl.DatabaseLawBookRepository
import me.bumiller.mol.data.repository.impl.DatabaseLawEntryRepository
import me.bumiller.mol.data.repository.impl.DatabaseLawSectionRepository
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
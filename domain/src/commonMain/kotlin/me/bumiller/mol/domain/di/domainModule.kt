package me.bumiller.mol.domain.di

import me.bumiller.mol.domain.GetBooksUsecase
import me.bumiller.mol.domain.GetInvitationsUsecase
import org.koin.dsl.module

/**
 * Koin module for the domain module.
 */
val domainModule = module {
    single { GetBooksUsecase(get()) }
    single { GetInvitationsUsecase(get(), get(), get()) }
}
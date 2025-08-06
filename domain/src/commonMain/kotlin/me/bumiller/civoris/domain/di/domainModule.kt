package me.bumiller.civoris.domain.di

import me.bumiller.civoris.domain.GetBooksUsecase
import me.bumiller.civoris.domain.GetInvitationsUsecase
import org.koin.dsl.module

/**
 * Koin module for the domain module.
 */
val domainModule = module {
    single { GetBooksUsecase(get()) }
    single { GetInvitationsUsecase(get(), get(), get()) }
}
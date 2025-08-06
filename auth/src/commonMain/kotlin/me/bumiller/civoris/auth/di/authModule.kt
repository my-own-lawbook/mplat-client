package me.bumiller.civoris.auth.di

import me.bumiller.civoris.auth.AuthService
import me.bumiller.civoris.auth.impl.KtorAuthService
import org.koin.dsl.module

/**
 * Koin module for the auth module.
 */
val authModule = module {
    single<AuthService> { KtorAuthService(get(), get()) }
}
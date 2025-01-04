package me.bumiller.mol.auth.di

import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.auth.impl.KtorAuthService
import org.koin.dsl.module

/**
 * Koin module for the auth module.
 */
val authModule = module {
    single<AuthService> { KtorAuthService(get(), get()) }
}
package me.bumiller.mol.network.di

import me.bumiller.mol.network.ServerStatusChecker
import me.bumiller.mol.network.impl.KtorServerStatusChecker
import org.koin.dsl.module

/**
 * Koin module for the network module.
 */
val networkModule = module {
    single<ServerStatusChecker> {
        KtorServerStatusChecker()
    }
}
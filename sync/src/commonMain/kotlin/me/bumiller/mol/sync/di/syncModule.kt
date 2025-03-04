package me.bumiller.mol.sync.di

import me.bumiller.mol.sync.SyncAdapter
import me.bumiller.mol.sync.SyncManager
import me.bumiller.mol.sync.impl.NetworkDatabaseSyncAdapter
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Koin module for the :sync module.
 */
val syncModule = module {
    single<SyncAdapter> {
        NetworkDatabaseSyncAdapter(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    platformSyncManager()
}

/**
 * Adds a dependency injection configuration for the platform dependant [SyncManager]
 */
internal expect fun Module.platformSyncManager()
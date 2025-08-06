package me.bumiller.civoris.sync.di

import me.bumiller.civoris.sync.SyncAdapter
import me.bumiller.civoris.sync.SyncManager
import me.bumiller.civoris.sync.impl.NetworkDatabaseSyncAdapter
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
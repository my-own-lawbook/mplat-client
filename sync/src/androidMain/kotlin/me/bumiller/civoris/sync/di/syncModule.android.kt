package me.bumiller.civoris.sync.di

import me.bumiller.civoris.sync.SyncManager
import me.bumiller.civoris.sync.impl.SyncWorker
import me.bumiller.civoris.sync.impl.WorkManagerSyncManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.Module

/**
 * Adds a dependency injection configuration for the platform dependant [SyncManager]
 */
internal actual fun Module.platformSyncManager() {
    worker { SyncWorker(androidContext(), get(), get()) }

    single<SyncManager> { WorkManagerSyncManager(androidContext()) }
}
package me.bumiller.civoris.sync.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.bumiller.civoris.sync.SyncManager
import me.bumiller.civoris.sync.impl.CoroutineSyncManager
import org.koin.core.module.Module

/**
 * Adds a dependency injection configuration for the platform dependant [SyncManager]
 */
internal actual fun Module.platformSyncManager() {
    single<SyncManager> { CoroutineSyncManager(CoroutineScope(Dispatchers.IO), get()) }
}
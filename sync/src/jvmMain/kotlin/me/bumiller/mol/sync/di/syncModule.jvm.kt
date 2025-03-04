package me.bumiller.mol.sync.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.bumiller.mol.sync.SyncManager
import me.bumiller.mol.sync.impl.CoroutineSyncManager
import org.koin.core.module.Module

/**
 * Adds a dependency injection configuration for the platform dependant [SyncManager]
 */
internal actual fun Module.platformSyncManager() {
    single<SyncManager> { CoroutineSyncManager(CoroutineScope(Dispatchers.IO), get()) }
}
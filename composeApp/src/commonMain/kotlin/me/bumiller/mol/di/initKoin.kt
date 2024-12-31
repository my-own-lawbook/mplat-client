package me.bumiller.mol.di

import me.bumiller.mol.data.di.dataModule
import me.bumiller.mol.database.di.databaseModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Central entrypoint to start the koin service
 *
 * @param config Koin config that may differ from platform to platform
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)

        modules(databaseModule, dataModule, appModule)
    }
}
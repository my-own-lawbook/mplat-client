package me.bumiller.mol.di

import me.bumiller.mol.auth.di.authModule
import me.bumiller.mol.data.di.dataModule
import me.bumiller.mol.database.di.databaseModule
import me.bumiller.mol.domain.di.domainModule
import me.bumiller.mol.feature.auth.di.authFeatureModule
import me.bumiller.mol.feature.dashboard.di.dashboardModule
import me.bumiller.mol.feature.home.di.homeModule
import me.bumiller.mol.feature.onboarding.di.onboardingModule
import me.bumiller.mol.network.di.networkModule
import me.bumiller.mol.settings.di.settingsModule
import me.bumiller.mol.sync.di.syncModule
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

        modules(
            dataModule,
            appModule,
            onboardingModule,
            networkModule,
            settingsModule,
            authModule,
            authFeatureModule,
            databaseModule,
            domainModule,
            dashboardModule,
            syncModule,
            homeModule
        )
    }
}
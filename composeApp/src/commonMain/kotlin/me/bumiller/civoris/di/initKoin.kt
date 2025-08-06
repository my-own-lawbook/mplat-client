package me.bumiller.civoris.di

import me.bumiller.civoris.auth.di.authModule
import me.bumiller.civoris.data.di.dataModule
import me.bumiller.civoris.database.di.databaseModule
import me.bumiller.civoris.domain.di.domainModule
import me.bumiller.civoris.feature.auth.di.authFeatureModule
import me.bumiller.civoris.feature.dashboard.di.dashboardModule
import me.bumiller.civoris.feature.home.di.homeModule
import me.bumiller.civoris.feature.onboarding.di.onboardingModule
import me.bumiller.civoris.network.di.networkModule
import me.bumiller.civoris.settings.di.settingsModule
import me.bumiller.civoris.sync.di.syncModule
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
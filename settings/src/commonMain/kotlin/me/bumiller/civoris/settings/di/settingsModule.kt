package me.bumiller.civoris.settings.di

import com.russhwolf.settings.Settings
import me.bumiller.civoris.settings.UserSettingsSource
import me.bumiller.civoris.settings.impl.UserSettingsSourceImpl
import org.koin.core.scope.Scope
import org.koin.dsl.module

/**
 * The koin module for the settings module.
 */
val settingsModule = module {
    single { instantiateSettings() }
    single<UserSettingsSource> { UserSettingsSourceImpl(get()) }
}

/**
 * Platform method to create an instance of the [Settings] interface
 */
expect fun Scope.instantiateSettings(): Settings
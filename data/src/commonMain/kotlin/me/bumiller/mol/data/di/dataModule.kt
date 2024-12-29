package me.bumiller.mol.data.di

import me.bumiller.mol.data.UserSettingsSource
import me.bumiller.mol.data.database.DatabaseUserSettingsSource
import org.koin.dsl.module

/**
 * The koin-module for the data module
 */
val dataModule = module {
    single<UserSettingsSource> {
        DatabaseUserSettingsSource(get())
    }
}
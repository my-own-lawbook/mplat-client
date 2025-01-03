package me.bumiller.mol.settings.di

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import org.koin.core.scope.Scope
import java.util.prefs.Preferences

/**
 * Platform method to create an instance of the [Settings] interface
 */
actual fun Scope.instantiateSettings(): Settings {
    val preferences = Preferences.userRoot()
    return PreferencesSettings(preferences)
}
package me.bumiller.civoris.settings

import kotlinx.coroutines.flow.StateFlow
import me.bumiller.civoris.model.settings.UserSettings

/**
 * Models a data source to access the settings of the user
 */
interface UserSettingsSource {

    /**
     * The current user settings.
     */
    val settings: StateFlow<UserSettings>

    /**
     * Updates the settings of the user
     */
    suspend fun update(settings: UserSettings)

}
package me.bumiller.mol.settings.impl

import com.eygraber.uri.Url
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import me.bumiller.mol.model.ColorMode
import me.bumiller.mol.model.ColorScheme
import me.bumiller.mol.model.ColorSchemeContrastLevel
import me.bumiller.mol.model.UserSettings
import me.bumiller.mol.settings.UserSettingsSource

private const val KeyMode = "color_mode"
private const val KeyScheme = "color_scheme"
private const val KeyContrastLevel = "contrast_level"
private const val KeyUrl = "backend_url"
private const val KeyAccess = "access_token"
private const val KeyRefresh = "refresh_token"

private val DefaultSettings =
    UserSettings(
        ColorMode.System,
        ColorScheme.App,
        ColorSchemeContrastLevel.Normal,
        null,
        null,
        null
    )

internal class UserSettingsSourceImpl(
    private val settingsSource: Settings
) : UserSettingsSource {

    override val settings: MutableSharedFlow<UserSettings>

    init {
        var initialSettings = getUserSettings()

        if (initialSettings == null) {
            saveUserSettings(DefaultSettings)
            initialSettings = DefaultSettings
        }

        settings = MutableStateFlow(initialSettings)
    }

    override suspend fun update(settings: UserSettings) {
        saveUserSettings(settings)
    }

    private fun getUserSettings(): UserSettings? = with(settingsSource) {
        val colorMode = getStringOrNull(KeyMode)
            ?.let { str -> ColorMode.entries.find { it.name.lowercase() == str } } ?: return null
        val colorScheme = getStringOrNull(KeyScheme)
            ?.let { str -> ColorScheme.entries.find { it.name.lowercase() == str } } ?: return null
        val contrastLevel = getStringOrNull(KeyContrastLevel)
            ?.let { str -> ColorSchemeContrastLevel.entries.find { it.name.lowercase() == str } }
            ?: return null
        val backendUrl = getStringOrNull(KeyUrl)
            ?.let {
                Url.parseOrNull(it) ?: return@with null
            }
        val accessToken = getStringOrNull(KeyAccess)
        val refreshToken = getStringOrNull(KeyRefresh)

        return UserSettings(
            colorMode,
            colorScheme,
            contrastLevel,
            backendUrl,
            accessToken,
            refreshToken
        )
    }

    private fun saveUserSettings(userSettings: UserSettings) = with(settingsSource) {
        putString(KeyMode, userSettings.colorMode.name.lowercase())
        putString(KeyScheme, userSettings.colorScheme.name.lowercase())
        putString(KeyContrastLevel, userSettings.contrastLevel.name.lowercase())

        userSettings.backendUrl?.let { putString(KeyUrl, it.toString()) }
        userSettings.accessToken?.let { putString(KeyAccess, it) }
        userSettings.refreshToken?.let { putString(KeyRefresh, it) }
    }

}
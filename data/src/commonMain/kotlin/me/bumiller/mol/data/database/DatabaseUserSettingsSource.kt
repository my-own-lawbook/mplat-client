package me.bumiller.mol.data.database

import com.eygraber.uri.Url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.bumiller.mol.data.UserSettingsSource
import me.bumiller.mol.database.daos.UserSettingsDao
import me.bumiller.mol.database.entities.UserSettingsEntity
import me.bumiller.mol.model.ColorMode
import me.bumiller.mol.model.ColorScheme
import me.bumiller.mol.model.UserSettings

internal class DatabaseUserSettingsSource(
    private val userSettingsDao: UserSettingsDao
) : UserSettingsSource {

    override val settings: Flow<UserSettings> = userSettingsDao.get()
        .map {
            if (it == null) {
                userSettingsDao.insert(toEntity(DefaultSettings))
                DefaultSettings
            } else {
                toModel(it)
            }
        }

    override suspend fun update(settings: UserSettings) {
        userSettingsDao.update(toEntity(settings))
    }

}

private val DefaultSettings = UserSettings(ColorMode.System, ColorScheme.App, null)

private fun toModel(entity: UserSettingsEntity): UserSettings =
    UserSettings(
        colorMode = ColorMode.valueOf(entity.colorMode.lowercase()),
        colorScheme = ColorScheme.valueOf(entity.colorScheme.lowercase()),
        backendUrl = entity.backendUrl?.let(Url::parseOrNull)
            ?: throw IllegalStateException("Tried to convert invalid URL to ")
    )

private fun toEntity(model: UserSettings): UserSettingsEntity =
    UserSettingsEntity(
        id = 1L,
        backendUrl = model.backendUrl?.toString(),
        colorMode = model.colorMode.name.lowercase(),
        colorScheme = model.colorScheme.name.lowercase()
    )
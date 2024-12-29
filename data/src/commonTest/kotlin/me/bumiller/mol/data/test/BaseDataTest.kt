package me.bumiller.mol.data.test

import com.eygraber.uri.Url
import io.mockk.mockk
import me.bumiller.mol.data.UserSettingsSource
import me.bumiller.mol.data.database.DatabaseUserSettingsSource
import me.bumiller.mol.database.daos.UserSettingsDao
import me.bumiller.mol.database.entities.UserSettingsEntity
import me.bumiller.mol.model.ColorMode
import me.bumiller.mol.model.ColorScheme
import me.bumiller.mol.model.UserSettings
import org.junit.jupiter.api.BeforeEach

/**
 * Base class for all tests in the data module
 */
abstract class BaseDataTest {

    /**
     * The mockked user settings dao
     */
    lateinit var settingsDao: UserSettingsDao

    /**
     * The to test user settings source
     */
    lateinit var settingsSource: UserSettingsSource

    @BeforeEach
    fun setup() {
        settingsDao = mockk()

        settingsSource = DatabaseUserSettingsSource(settingsDao)
    }

    /**
     * Creates a [UserSettingsEntity]
     *
     * @param key The key to customize the entity
     * @return The entity
     */
    fun settingsEntity(key: Long) = UserSettingsEntity(
        id = key,
        backendUrl = "https://www.example.com/$key",
        colorMode = "light",
        colorScheme = "dynamic"
    )

    /**
     * Creates a [UserSettings]
     *
     * @param key The key to customize the entity
     * @return The entity
     */
    fun settingsModel(key: Long) = UserSettings(
        backendUrl = Url.parse("https://www.example.com/$key"),
        colorMode = ColorMode.Light,
        colorScheme = ColorScheme.Dynamic
    )

}
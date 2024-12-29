package me.bumiller.mol.data.database

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.bumiller.mol.data.test.BaseDataTest
import me.bumiller.mol.database.entities.UserSettingsEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DatabaseUserSettingsSourceTest : BaseDataTest() {

    @Test
    fun first_settings_emission_inserts_row() = runTest {
        every { settingsDao.get() } returns flowOf(null)
        coEvery { settingsDao.insert(any()) } returns 1

        settingsSource.settings.first()

        coVerify(exactly = 1) { settingsDao.insert(any()) }
    }

    @Test
    fun setting_emissions_return_mapped_entity() = runTest {
        val entity = settingsEntity(5L)
        every { settingsDao.get() } returns flowOf(entity)

        val model = settingsSource.settings.first()

        Assertions.assertEquals(entity.colorMode, model.colorMode.name.lowercase())
        Assertions.assertEquals(entity.colorScheme, model.colorScheme.name.lowercase())
        Assertions.assertEquals(entity.backendUrl, model.backendUrl.toString())
    }

    @Test
    fun update_maps_and_delegates_to_update() = runTest {
        val model = settingsModel(9L)
        val entitySlot = slot<UserSettingsEntity>()

        coEvery { settingsDao.update(capture(entitySlot)) } returns 1

        settingsSource.update(model)

        coVerify(exactly = 1) { settingsDao.update(any()) }

        Assertions.assertEquals(model.backendUrl.toString(), entitySlot.captured.backendUrl)
        Assertions.assertEquals(model.colorMode.name.lowercase(), entitySlot.captured.colorMode)
        Assertions.assertEquals(model.colorScheme.name.lowercase(), entitySlot.captured.colorScheme)
    }

}
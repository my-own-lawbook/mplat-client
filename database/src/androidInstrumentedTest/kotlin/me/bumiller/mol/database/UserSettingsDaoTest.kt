package me.bumiller.mol.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import me.bumiller.mol.database.test.BaseRoomTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSettingsDaoTest : BaseRoomTest() {

    @Test
    fun insert_works() = runTest {
        userSettingsDao.insert(userSetting(1L))
        userSettingsDao.insert(userSetting(2L))
    }

    @Test
    fun get_returns_nonempty_flow() = runTest {
        val entity = userSetting(1L)
        userSettingsDao.insert(entity)

        val flow = userSettingsDao.get()

        flow.firstOrNull().let {
            Assert.assertEquals(it, entity)
        }
    }

    @Test
    fun get_returns_null() = runTest {
        val flow = userSettingsDao.get()

        flow.firstOrNull().let(Assert::assertNull)
    }

    @Test
    fun update_updates() = runTest {
        val entity = userSetting(1L)
        val updated = entity.copy(backendUrl = "https://new-backend-url.com")

        userSettingsDao.insert(entity)
        val colsUpdated = userSettingsDao.update(updated)

        userSettingsDao.get().firstOrNull()?.backendUrl?.let {
            Assert.assertEquals("https://new-backend-url.com", it)
        }
        Assert.assertEquals(1, colsUpdated)
    }

}
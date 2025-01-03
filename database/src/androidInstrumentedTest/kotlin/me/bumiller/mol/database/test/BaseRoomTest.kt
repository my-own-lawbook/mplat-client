package me.bumiller.mol.database.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import me.bumiller.mol.database.MolDatabase
import org.junit.Before

/**
 * Sets up the database and daos
 */
abstract class BaseRoomTest {

    /**
     * The in-memory database
     */
    private lateinit var db: MolDatabase

    /**
     * The dao for the user settings entity
     */
    lateinit var userSettingsDao: UserSettingsDao

    @Before
    fun before() {
        println("Inside before()")
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MolDatabase::class.java
        ).build()

        userSettingsDao = db.userSettingsDao()
    }

    /**
     * Creates a [UserSettingsEntity]
     *
     * @param key The key to customize the entity
     * @return The entity
     */
    fun userSetting(key: Long) = UserSettingsEntity(
        id = key,
        backendUrl = "https://www.example.com/$key",
        colorMode = "system",
        colorScheme = "light",
        contrastLevel = "normal"
    )

}
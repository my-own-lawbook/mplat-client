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

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MolDatabase::class.java
        ).build()
    }

}
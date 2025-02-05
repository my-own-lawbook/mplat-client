package me.bumiller.mol.database.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import me.bumiller.mol.database.MolDatabase
import me.bumiller.mol.database.dao.BookDao
import me.bumiller.mol.database.dao.EntryDao
import me.bumiller.mol.database.dao.ForeignUserDao
import me.bumiller.mol.database.dao.InvitationDao
import me.bumiller.mol.database.dao.SectionDao
import org.junit.Before

/**
 * Sets up the database and daos
 */
abstract class BaseRoomTest {

    /**
     * The in-memory database
     */
    private lateinit var db: MolDatabase

    private lateinit var bookDao: BookDao
    private lateinit var entryDao: EntryDao
    private lateinit var foreignUserDao: ForeignUserDao
    private lateinit var invitationDao: InvitationDao
    private lateinit var sectionDao: SectionDao

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MolDatabase::class.java
        ).build()
    }

}
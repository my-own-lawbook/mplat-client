package me.bumiller.mol.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import me.bumiller.mol.database.daos.UserSettingsDao
import me.bumiller.mol.database.entities.UserSettingsEntity

/**
 * Class that represents the sql database
 */
@Database(
    entities = [UserSettingsEntity::class],
    version = MolDatabase.DB_VERSION
)
@ConstructedBy(MolDatabaseConstructor::class)
abstract class MolDatabase : RoomDatabase() {

    /**
     * Gets the [UserSettingsEntity] for this database
     */
    abstract fun userSettingsDao(): UserSettingsDao

    companion object {

        /**
         * The database version
         */
        const val DB_VERSION = 2

        /**
         * The database name
         */
        const val DB_NAME = "mol_db"

    }

}
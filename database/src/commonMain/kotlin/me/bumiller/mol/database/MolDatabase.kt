package me.bumiller.mol.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Class that represents the sql database
 */
@Database(
    entities = [],
    version = MolDatabase.DB_VERSION
)
@ConstructedBy(MolDatabaseConstructor::class)
abstract class MolDatabase : RoomDatabase() {

    companion object {

        /**
         * The database version
         */
        const val DB_VERSION = 1

        /**
         * The database name
         */
        const val DB_NAME = "mol_db"

    }

}
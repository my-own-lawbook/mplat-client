package me.bumiller.mol.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.database.entities.SectionEntity

/**
 * Class that represents the sql database
 */
@Database(
    entities = [BookEntity::class, EntryEntity::class, ForeignUserEntity::class, InvitationEntity::class, SectionEntity::class],
    version = MolDatabase.DB_VERSION
)
@ConstructedBy(MolDatabaseConstructor::class)
abstract class MolDatabase : RoomDatabase() {

    companion object {

        /**
         * The database version
         */
        const val DB_VERSION = 3

        /**
         * The database name
         */
        const val DB_NAME = "mol_db"

    }

}
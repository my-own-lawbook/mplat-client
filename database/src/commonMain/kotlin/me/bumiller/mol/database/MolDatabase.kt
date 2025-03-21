package me.bumiller.mol.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.bumiller.mol.database.converter.InstantConverter
import me.bumiller.mol.database.converter.LocalDateConverter
import me.bumiller.mol.database.dao.BookDao
import me.bumiller.mol.database.dao.BookMemberCrossrefDao
import me.bumiller.mol.database.dao.EntryDao
import me.bumiller.mol.database.dao.ForeignUserDao
import me.bumiller.mol.database.dao.InvitationDao
import me.bumiller.mol.database.dao.SectionDao
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.entities.BookMemberCrossref
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.database.entities.SectionEntity

/**
 * Class that represents the sql database
 */
@Database(
    entities = [BookEntity::class, EntryEntity::class, ForeignUserEntity::class, InvitationEntity::class, SectionEntity::class, BookMemberCrossref::class],
    version = MolDatabase.DB_VERSION
)
@ConstructedBy(MolDatabaseConstructor::class)
@TypeConverters(
    value = [LocalDateConverter::class, InstantConverter::class]
)
abstract class MolDatabase : RoomDatabase() {

    companion object {

        /**
         * The database version
         */
        const val DB_VERSION = 5

        /**
         * The database name
         */
        const val DB_NAME = "mol_db"

    }


    /**
     * Retrieves the [BookDao] for the database.
     *
     * @return The dao
     */
    abstract fun bookDao(): BookDao

    /**
     * Retrieves the [EntryDao] for the database.
     *
     * @return The dao
     */
    abstract fun entryDao(): EntryDao

    /**
     * Retrieves the [ForeignUserDao] for the database.
     *
     * @return The dao
     */
    abstract fun foreignUserDao(): ForeignUserDao

    /**
     * Retrieves the [InvitationDao] for the database.
     *
     * @return The dao
     */
    abstract fun invitationDao(): InvitationDao

    /**
     * Retrieves the [SectionDao] for the database.
     *
     * @return The dao
     */
    abstract fun sectionDao(): SectionDao

    /**
     * Retrieves the [BookMemberCrossrefDao] for the database
     * @return The dao
     */
    abstract fun bookMemberCrossrefDao(): BookMemberCrossrefDao

}
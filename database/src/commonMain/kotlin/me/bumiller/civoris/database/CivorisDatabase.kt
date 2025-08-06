package me.bumiller.civoris.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.bumiller.civoris.database.converter.InstantConverter
import me.bumiller.civoris.database.converter.LocalDateConverter
import me.bumiller.civoris.database.dao.BookDao
import me.bumiller.civoris.database.dao.BookMemberCrossrefDao
import me.bumiller.civoris.database.dao.EntryDao
import me.bumiller.civoris.database.dao.ForeignUserDao
import me.bumiller.civoris.database.dao.InvitationDao
import me.bumiller.civoris.database.dao.SectionDao
import me.bumiller.civoris.database.entities.BookEntity
import me.bumiller.civoris.database.entities.BookMemberCrossref
import me.bumiller.civoris.database.entities.EntryEntity
import me.bumiller.civoris.database.entities.ForeignUserEntity
import me.bumiller.civoris.database.entities.InvitationEntity
import me.bumiller.civoris.database.entities.SectionEntity

/**
 * Class that represents the sql database
 */
@Database(
    entities = [BookEntity::class, EntryEntity::class, ForeignUserEntity::class, InvitationEntity::class, SectionEntity::class, BookMemberCrossref::class],
    version = CivorisDatabase.DB_VERSION
)
@ConstructedBy(CivorisDatabaseConstructor::class)
@TypeConverters(
    value = [LocalDateConverter::class, InstantConverter::class]
)
abstract class CivorisDatabase : RoomDatabase() {

    companion object {

        /**
         * The database version
         */
        const val DB_VERSION = 5

        /**
         * The database name
         */
        const val DB_NAME = "civoris_db"

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
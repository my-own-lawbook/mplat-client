package me.bumiller.mol.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import me.bumiller.mol.database.CivorisDatabase
import me.bumiller.mol.database.dao.BookDao
import me.bumiller.mol.database.dao.BookMemberCrossrefDao
import me.bumiller.mol.database.dao.EntryDao
import me.bumiller.mol.database.dao.ForeignUserDao
import me.bumiller.mol.database.dao.InvitationDao
import me.bumiller.mol.database.dao.SectionDao
import me.bumiller.mol.database.dao.base.OneToManyDao
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.entities.SectionEntity
import org.koin.core.scope.Scope
import org.koin.dsl.module

/**
 * Platform specific method to create the database builder
 */
expect fun Scope.databaseBuilder(): RoomDatabase.Builder<CivorisDatabase>

/**
 * The module for the database
 */
val databaseModule = module {
    single { databaseBuilder() }

    single {
        database(get())
    }

    single<BookDao> { get<CivorisDatabase>().bookDao() }
    single<BookMemberCrossrefDao> { get<CivorisDatabase>().bookMemberCrossrefDao() }
    single<EntryDao> { get<CivorisDatabase>().entryDao() }
    single<ForeignUserDao> { get<CivorisDatabase>().foreignUserDao() }
    single<InvitationDao> { get<CivorisDatabase>().invitationDao() }
    single<SectionDao> { get<CivorisDatabase>().sectionDao() }

    single<OneToManyDao<BookEntity, EntryEntity>> {
        OneToManyDao(
            get(),
            get(),
            EntryEntity::parentBookId
        )
    }
    single<OneToManyDao<EntryEntity, SectionEntity>> {
        OneToManyDao(
            get(),
            get(),
            SectionEntity::parentEntryId
        )
    }
}

/**
 * Creates the database off of the platform database builder
 */
private fun database(builder: RoomDatabase.Builder<CivorisDatabase>): CivorisDatabase = builder.apply {
    fallbackToDestructiveMigration(true)
    setDriver(BundledSQLiteDriver())
}.build()
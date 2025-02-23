package me.bumiller.mol.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import me.bumiller.mol.database.MolDatabase
import me.bumiller.mol.database.dao.BookDao
import me.bumiller.mol.database.dao.BookMemberCrossrefDao
import me.bumiller.mol.database.dao.EntryDao
import me.bumiller.mol.database.dao.ForeignUserDao
import me.bumiller.mol.database.dao.InvitationDao
import me.bumiller.mol.database.dao.SectionDao
import org.koin.core.scope.Scope
import org.koin.dsl.module

/**
 * Platform specific method to create the database builder
 */
expect fun Scope.databaseBuilder(): RoomDatabase.Builder<MolDatabase>

/**
 * The module for the database
 */
val databaseModule = module {
    single { databaseBuilder() }

    single {
        database(get())
    }

    single<BookDao> { get<MolDatabase>().bookDao() }
    single<BookMemberCrossrefDao> { get<MolDatabase>().bookMemberCrossrefDao() }
    single<EntryDao> { get<MolDatabase>().entryDao() }
    single<ForeignUserDao> { get<MolDatabase>().foreignUserDao() }
    single<InvitationDao> { get<MolDatabase>().invitationDao() }
    single<SectionDao> { get<MolDatabase>().sectionDao() }
}

/**
 * Creates the database off of the platform database builder
 */
private fun database(builder: RoomDatabase.Builder<MolDatabase>): MolDatabase = builder.apply {
    fallbackToDestructiveMigration(true)
    setDriver(BundledSQLiteDriver())
}.build()
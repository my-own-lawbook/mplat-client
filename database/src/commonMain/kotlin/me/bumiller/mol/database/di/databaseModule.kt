package me.bumiller.mol.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import me.bumiller.mol.database.MolDatabase
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

    single {
        get<MolDatabase>().userSettingsDao()
    }
}

/**
 * Creates the database off of the platform database builder
 */
private fun database(builder: RoomDatabase.Builder<MolDatabase>): MolDatabase = builder.apply {
    fallbackToDestructiveMigration(true)
    setDriver(BundledSQLiteDriver())
}.build()
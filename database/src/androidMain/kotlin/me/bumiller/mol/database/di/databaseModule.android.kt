package me.bumiller.mol.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import me.bumiller.mol.database.MolDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

/**
 * Platform specific method to create the database builder
 */
actual fun Scope.databaseBuilder(): RoomDatabase.Builder<MolDatabase> = Room.databaseBuilder(
    context = androidContext(),
    klass = MolDatabase::class.java,
    name = androidContext().getDatabasePath(MolDatabase.DB_NAME).absolutePath
)
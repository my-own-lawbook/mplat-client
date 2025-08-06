package me.bumiller.civoris.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import me.bumiller.civoris.database.CivorisDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

/**
 * Platform specific method to create the database builder
 */
actual fun Scope.databaseBuilder(): RoomDatabase.Builder<CivorisDatabase> = Room.databaseBuilder(
    context = androidContext(),
    klass = CivorisDatabase::class.java,
    name = androidContext().getDatabasePath(CivorisDatabase.DB_NAME).absolutePath
)
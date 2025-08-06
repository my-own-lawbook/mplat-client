package me.bumiller.civoris.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import me.bumiller.civoris.database.CivorisDatabase
import org.koin.core.scope.Scope
import java.io.File

/**
 * Platform specific method to create the database builder
 */
actual fun Scope.databaseBuilder(): RoomDatabase.Builder<CivorisDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), CivorisDatabase.DB_NAME)
    return Room.databaseBuilder(dbFile.absolutePath)
}
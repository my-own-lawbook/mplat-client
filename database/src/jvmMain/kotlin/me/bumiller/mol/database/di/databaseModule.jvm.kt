package me.bumiller.mol.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import me.bumiller.mol.database.MolDatabase
import org.koin.core.scope.Scope
import java.io.File

/**
 * Platform specific method to create the database builder
 */
actual fun Scope.databaseBuilder(): RoomDatabase.Builder<MolDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), MolDatabase.DB_NAME)
    return Room.databaseBuilder(dbFile.absolutePath)
}
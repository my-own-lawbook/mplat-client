package me.bumiller.mol.database

import androidx.room.RoomDatabaseConstructor

/**
 * Platform-dependant constructor for the database
 */
// For suppression, see https://developer.android.com/kotlin/multiplatform/room
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object MolDatabaseConstructor : RoomDatabaseConstructor<MolDatabase> {

    override fun initialize(): MolDatabase

}
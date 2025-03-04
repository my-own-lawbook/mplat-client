package me.bumiller.mol.sync

import me.bumiller.mol.model.sync.SyncResult

/**
 * Adapter that manages the transferring of resources from the remote to the local database.
 */
internal fun interface SyncAdapter {

    /**
     * Performs the synchronization.
     *
     * @return A result describing the turnout of the synchronization
     */
    suspend fun performSync(): SyncResult

}
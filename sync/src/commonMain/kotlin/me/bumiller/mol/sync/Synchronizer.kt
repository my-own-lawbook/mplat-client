package me.bumiller.mol.sync

import me.bumiller.mol.sync.model.SyncResult

/**
 * Manages synchronizing a list of responses from the api to the local database.
 */
fun interface Synchronizer {

    /**
     * Synchronizes the responses.
     *
     * @return The sync result, indicating the failure that occurred
     */
    suspend fun synchronize(): SyncResult

}
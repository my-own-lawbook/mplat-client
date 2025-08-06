package me.bumiller.civoris.sync

import me.bumiller.civoris.model.sync.SyncResult

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
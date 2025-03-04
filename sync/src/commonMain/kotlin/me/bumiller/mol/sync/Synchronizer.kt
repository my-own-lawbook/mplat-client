package me.bumiller.mol.sync

/**
 * Manages synchronizing a list of responses from the api to the local database.
 */
fun interface Synchronizer {

    /**
     * Synchronizes the responses.
     *
     * @return Whether the responses were able to be fetched from the api
     */
    suspend fun synchronize(): Boolean

}
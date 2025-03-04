package me.bumiller.mol.sync.model

import me.bumiller.mol.network.model.NetworkResponse

/**
 * Models results from a sync action.
 */
sealed class SyncResult(

    /**
     * Whether the sync failed.
     */
    val isFailed: Boolean

) {

    /**
     * The sync finished correctly.
     */
    data object Success : SyncResult(false)

    /**
     * The sync aborted due to a network error.
     *
     * @param result The result that signifies the failure. Guaranteed to be either [NetworkResponse.NetworkError] or [NetworkResponse.HttpError].
     */
    data class Network(val result: NetworkResponse<*>) : SyncResult(true)

    /**
     * The sync aborted due to a data validation error.
     */
    data object Data : SyncResult(true)

}
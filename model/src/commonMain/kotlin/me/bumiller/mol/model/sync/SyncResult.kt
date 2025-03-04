package me.bumiller.mol.model.sync

/**
 * Models results from a sync action.
 */
enum class SyncResult(

    /**
     * Whether the sync failed.
     */
    val isFailed: Boolean

) {

    /**
     * The sync finished correctly.
     */
    Success(false),

    /**
     * The sync aborted due to a network error.
     */
    Network(true),

    /**
     * The sync aborted due to a data validation error.
     */
    Data(true)

}
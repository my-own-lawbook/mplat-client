package me.bumiller.mol.model.sync

/**
 * Information about a started sync job. Describes the current state.
 */
sealed interface SyncJobInfo {

    /**
     * Means the job is currently scheduled, and not running.
     */
    data object Scheduled : SyncJobInfo

    /**
     * Means the job is currently running.
     */
    data object Running : SyncJobInfo

    /**
     * Means the job has finished running.
     *
     * @param result The result from the sync adapter
     */
    data class Finished(val result: SyncResult) : SyncJobInfo

    /**
     * Means the job was never run.
     */
    data object Cancelled : SyncJobInfo

}
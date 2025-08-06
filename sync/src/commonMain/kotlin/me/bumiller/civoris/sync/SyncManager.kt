package me.bumiller.civoris.sync

import kotlinx.coroutines.flow.Flow
import me.bumiller.civoris.model.sync.SyncJobInfo

/**
 * Adapter that manages scheduling, stopping and observing a sync job.
 */
interface SyncManager {

    /**
     * Schedules a new sync for the specified identifier.
     *
     * @return A flow containing the current state of the job. Once [SyncJobInfo.Finished] is emitted, the flow will no longer emit.
     */
    fun scheduleSync(): Flow<SyncJobInfo>

    /**
     * Creates a flow that emits true everytime a worker failed.
     *
     * @return The described flow
     */
    fun workerFailed(): Flow<Boolean>

    /**
     * Checks whether a sync job is active.
     *
     * @return A flow containing whether a sync jib is active to a certain point
     */
    fun isSyncJobActive(): Flow<Boolean>

}
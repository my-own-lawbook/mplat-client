package me.bumiller.mol.sync

import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Adapter that manages scheduling, stopping and observing a sync job.
 */
@OptIn(ExperimentalUuidApi::class)
interface SyncManager {

    /**
     * Schedules a new sync for the specified identifier.
     *
     * @return A flow containing the current state of the job. Once [SyncJobInfo.Finished] is emitted, the flow will no longer emit.
     */
    fun scheduleSync(identifier: Uuid): Flow<SyncJobInfo>

    /**
     * Stops the sync job.
     *
     * @param identifier The identifier of the sync job.
     */
    fun stopSync(identifier: Uuid)

    /**
     * Checks whether a sync job is active.
     *
     * @return A flow containing whether a sync jib is active to a certain point
     */
    fun isSyncJobActive(): Flow<Boolean>

}
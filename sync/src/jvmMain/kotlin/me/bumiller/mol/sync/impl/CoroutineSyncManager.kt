package me.bumiller.mol.sync.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.bumiller.mol.model.sync.SyncJobInfo
import me.bumiller.mol.sync.SyncAdapter
import me.bumiller.mol.sync.SyncManager
import java.util.LinkedList
import java.util.Queue
import java.util.UUID

internal class CoroutineSyncManager(
    private val scope: CoroutineScope,
    private val syncAdapter: SyncAdapter
) : SyncManager {

    private val jobQueue: Queue<JobWithId> = LinkedList()

    private val jobActiveFlow = MutableStateFlow(false)

    private val syncFailedFlow = MutableStateFlow(false)

    private fun updateActiveFlow() {
        jobActiveFlow.value = jobQueue.isNotEmpty()
    }

    override fun scheduleSync(): Flow<SyncJobInfo> {
        val identifier = UUID.randomUUID()
        val flow = MutableStateFlow<SyncJobInfo>(SyncJobInfo.Scheduled)

        val job = scope.launch {
            flow.emit(SyncJobInfo.Running)
            val result = syncAdapter.performSync()

            syncFailedFlow.emit(result.isFailed)

            val info = SyncJobInfo.Finished(result)
            flow.emit(info)
            jobQueue.removeIf { it.uuid == identifier }
            updateActiveFlow()
        }

        val jobWithId = JobWithId(job, identifier)
        jobQueue.add(jobWithId)
        updateActiveFlow()

        return flow
    }

    override fun workerFailed(): Flow<Boolean> = syncFailedFlow

    override fun isSyncJobActive(): Flow<Boolean> = jobActiveFlow

    data class JobWithId(
        val job: Job,
        val uuid: UUID
    )

}
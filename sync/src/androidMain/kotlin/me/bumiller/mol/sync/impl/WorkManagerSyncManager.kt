package me.bumiller.mol.sync.impl

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import me.bumiller.mol.model.sync.SyncJobInfo
import me.bumiller.mol.model.sync.SyncResult
import me.bumiller.mol.sync.SyncManager
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
internal class WorkManagerSyncManager(
    context: Context
) : SyncManager {

    private val workManager by lazy {
        WorkManager.getInstance(context)
    }

    override fun scheduleSync(identifier: Uuid): Flow<SyncJobInfo> {
        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setId(identifier.toJavaUuid())
            .addTag(SYNC_TAG)
            .build()

        workManager.enqueue(workRequest)

        return workManager.getWorkInfoByIdFlow(identifier.toJavaUuid())
            .map { workInfo ->
                workInfo?.toSyncJobInfo()
                    ?: throw IllegalStateException("Did not find a scheduled or for a uuid.")
            }
    }

    private fun WorkInfo.toSyncJobInfo() = when (state) {
        WorkInfo.State.ENQUEUED,
        WorkInfo.State.BLOCKED -> SyncJobInfo.Scheduled

        WorkInfo.State.RUNNING -> SyncJobInfo.Running
        WorkInfo.State.SUCCEEDED -> mapOutputData(outputData)
        WorkInfo.State.FAILED -> mapOutputData(outputData)
        WorkInfo.State.CANCELLED -> SyncJobInfo.Cancelled
    }

    private fun mapOutputData(data: Data): SyncJobInfo =
        data.getString(SyncWorker.KEY_OUTPUT_DATA)?.let {
            SyncJobInfo.Finished(SyncResult.valueOf(it))
        } ?: throw IllegalStateException("Worker finished successfully with no data")

    override fun stopSync(identifier: Uuid) {
        workManager.cancelWorkById(identifier.toJavaUuid())
    }

    private val workerFailedFlow = callbackFlow {
        val initiallyRunningWorkers = mutableSetOf<UUID>()
        val callback = FlowCollector<WorkInfo?> { workInfo ->
            println("Got work info $workInfo and initially running workers $initiallyRunningWorkers")
            val jobInfo = workInfo?.toSyncJobInfo()

            if (jobInfo == SyncJobInfo.Running) {
                initiallyRunningWorkers.add(workInfo.id)
            }

            if (jobInfo is SyncJobInfo.Finished && workInfo.id in initiallyRunningWorkers) {
                channel.send(jobInfo.result.isFailed)
            }
        }

        workManager.getWorkInfosByTagFlow(SYNC_TAG).collect { workInfos ->
            workInfos.forEach { workInfo ->
                workManager.getWorkInfoByIdFlow(workInfo.id)
                    .collect(callback)
            }
        }
    }

    override fun workerFailed(): Flow<Boolean> = workerFailedFlow

    override fun isSyncJobActive(): Flow<Boolean> = workManager
        .getWorkInfosByTagFlow(SYNC_TAG)
        .map { infos ->
            infos.any { it.state == WorkInfo.State.RUNNING }
        }

    companion object {

        private const val SYNC_TAG = "me.bumiller.mol.sync.work_tag"

    }

}